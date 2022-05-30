package DmN.ICA.vodka;

import DmN.ICA.vodka.api.MinecraftVersion;
import DmN.ICA.vodka.api.VodkaMod;
import DmN.ICA.vodka.impl.EnvType;
import DmN.ICA.vodka.impl.LoaderType;
import DmN.ICA.vodka.impl.VodkaClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public abstract class VodkaLoader implements VodkaMod.FirstInitializer, VodkaMod.CommonInitializer, VodkaMod.ClientInitializer, VodkaMod.ServerInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Vodka[Loader/Api]");
    public static @NotNull VodkaLoader INSTANCE;
    public final @NotNull VodkaClassLoader loader;

    protected VodkaLoader(@NotNull VodkaClassLoader loader) {
        this.loader = loader;
    }

    @Override
    public void firstInit() {
        LOGGER.info("Called FirstInit");
    }

    @Override
    public void commonInit() {
        LOGGER.info("Called CommonInit");
    }

    @Override
    public void clientInit() {
        LOGGER.info("Called ClientInit");
    }

    @Override
    public void serverInit() {
        LOGGER.info("Called ServerInit");
    }

    public abstract @NotNull MinecraftVersion getMCVersion();

    public @NotNull LoaderType getLoaderType() {
        return LoaderType.Unknown;
    }

    public abstract @NotNull EnvType getEnvironment();

    public abstract @NotNull Path getModsDir();

    public abstract @NotNull Path getConfigDir();
}
