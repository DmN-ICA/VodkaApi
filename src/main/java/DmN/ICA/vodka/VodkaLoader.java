package DmN.ICA.vodka;

import DmN.ICA.vodka.api.JsonObjectParser;
import DmN.ICA.vodka.impl.*;
import DmN.ICA.vodka.json.api.VodkaMod;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class VodkaLoader implements VodkaMod.FirstInitializer, VodkaMod.CommonInitializer, VodkaMod.ClientInitializer, VodkaMod.ServerInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Vodka[Loader/Api]");
    public static VodkaLoader INSTANCE;
    public final VodkaClassLoader loader;
    public List<VodkaMod> mods = new ArrayList<>();

    protected VodkaLoader(@NotNull VodkaClassLoader loader) {
        this.loader = loader;
    }

    @Override
    public void firstInit() {
        LOGGER.info("FirstInit started!");
        try {
            Enumeration<URL> enumeration = this.getClass().getClassLoader().getResources("vodka.mod.json");
            while (enumeration.hasMoreElements()) {
                JsonObjectParser jparser = JsonObjectParser.create(new JsonParser().parse(new FileReader(enumeration.nextElement().getFile())).getAsJsonObject());
                VodkaModImpl mod = new VodkaModImpl();
                mod.id = jparser.getString("id");
                mod.version = jparser.getInt("version");
                AtomicBoolean skip = new AtomicBoolean(false);
                this.mods.stream().filter(m -> m.id().equals(mod.id)).findFirst().ifPresent(m -> skip.set(m.version() >= mod.version));
                if (skip.get())
                    continue;
                mod.type = VodkaMod.Type.valueOf(jparser.getString("type").toUpperCase());
                mod.modClass = jparser.getString("class");
                mod.name = jparser.getString("name");
                mod.description = jparser.getString("description");
                mod.authors = new ArrayList<>();
                jparser.getArrIterator("authors").forEachRemaining(e -> mod.authors.add(e.getAsString()));
                mod.highMCVersion = new MinecraftVersion(jparser.getString("highMCVersion"));
                mod.lowMCVersion = new MinecraftVersion(jparser.getString("lowMCVersion"));
                mod.loadPost = new ArrayList<>();
                jparser.getArrIterator("loadPost").forEachRemaining(e -> mod.loadPost.add(e.getAsString()));
                mod.loadPrev = new ArrayList<>();
                jparser.getArrIterator("loadPrev").forEachRemaining(e -> mod.loadPrev.add(e.getAsString()));
                mod.dependencies = new ArrayList<>();
                jparser.getArrIterator("dependencies").forEachRemaining(e -> mod.dependencies.add(e.getAsString()));
                this.mods.add(mod);
            }

            List<VodkaMod> loads = new ArrayList<>();
            for (VodkaMod mod : this.mods)
                sortForLoad(loads, mod);
            this.mods = loads;

            callInit(VodkaMod.FirstInitializer.class, "firstInit");
        } catch (Exception e) {
            LOGGER.info("FirstInit failed!");
            throw new VodkaLoaderRuntimeException(e);
        }
        LOGGER.info("FirstInit completed!");
    }

    @Override
    public void commonInit() {
        LOGGER.info("CommonInit started!");
        try {
            callInit(VodkaMod.CommonInitializer.class, "commonInit");
        } catch (Exception e) {
            LOGGER.info("CommonInit failed!");
            throw new VodkaLoaderRuntimeException(e);
        }
        LOGGER.info("CommonInit completed!");
    }

    @Override
    public void clientInit() {
        LOGGER.info("ClientInit started!");
        try {
            callInit(VodkaMod.ClientInitializer.class, "clientInit");
        } catch (Exception e) {
            LOGGER.info("ClientInit failed!");
            throw new VodkaLoaderRuntimeException(e);
        }
        LOGGER.info("ClientInit completed!");
    }

    @Override
    public void serverInit() {
        LOGGER.info("ServerInit started!");
        try {
            callInit(VodkaMod.ServerInitializer.class, "serverInit");
        } catch (Exception e) {
            LOGGER.info("ServerInit failed!");
            throw new VodkaLoaderRuntimeException(e);
        }
        LOGGER.info("ServerInit completed!");
    }

    public abstract @NotNull MinecraftVersion getMCVersion();

    public @NotNull LoaderType getLoaderType() {
        return LoaderType.Unknown;
    }

    public abstract @NotNull EnvType getEnvironment();

    public abstract @NotNull Path getModsDir();

    public abstract @NotNull Path getConfigDir();

    protected void callInit(Class<?> clazz, String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (VodkaMod mod : this.mods)
            if (mod.modClass() != null && Class.forName(mod.modClass(), true, loader).isAssignableFrom(clazz))
                clazz.getMethod(name).invoke(mod.instance());
    }

    protected void sortForLoad(List<VodkaMod> loads, VodkaMod last) {
        if (loads.stream().anyMatch(mod -> mod.id().equals(last.id())))
            return;
        for (String mod : last.loadPost())
            this.sortForLoad(loads, this.mods.stream().filter(m -> m.id().equals(mod)).findFirst().get());
        if (!last.loadPrev().isEmpty()) {
            int minId = loads.size() - 1;
            for (String mod : last.loadPrev())
                minId = Math.min(minId, this.mods.indexOf(this.mods.stream().filter(m -> m.id().equals(mod)).findFirst().get()));
            List<VodkaMod> tmp0 = new ArrayList<>();
            List<VodkaMod> tmp1 = new ArrayList<>();
            for (int i = 0; i < minId; i++)
                tmp0.add(loads.get(i));
            for (int i = minId; i < loads.size(); i++)
                tmp1.add(loads.get(i));
            loads.clear();
            loads.addAll(tmp0);
            loads.add(last);
            loads.addAll(tmp1);
        } else
            loads.add(last);
    }

    public static class VodkaLoaderRuntimeException extends RuntimeException {
        public VodkaLoaderRuntimeException(String message) {
            super(message);
        }

        public VodkaLoaderRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public VodkaLoaderRuntimeException(Throwable cause) {
            super(cause);
        }
    }
}
