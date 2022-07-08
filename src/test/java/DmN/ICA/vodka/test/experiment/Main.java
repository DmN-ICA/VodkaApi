package DmN.ICA.vodka.test.experiment;

import DmN.ICA.vodka.VodkaLoader;
import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.api.MinecraftVersion;
import DmN.ICA.vodka.classloader.VodkaClassLoader;
import DmN.ICA.vodka.impl.VodkaModImpl;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        var loader = new Loader();

        var mod0 = new VodkaModImpl();
        mod0.id = "mod0";
        loader.mods.add(mod0);

        var mod1 = new VodkaModImpl();
        mod1.id = "mod1";
        mod1.loadPrev.add("mod0");
        loader.mods.add(mod1);

        var mod2 = new VodkaModImpl();
        mod2.id = "mod2";
        mod2.loadPost.add("mod0");
        loader.mods.add(mod2);

        var mod3 = new VodkaModImpl();
        mod3.id = "mod3";
        mod3.loadPrev.add("mod2");
        mod3.loadPost.add("mod1");
        loader.mods.add(mod3);

        var mod4 = new VodkaModImpl();
        mod4.id = "mod4";
        mod4.loadPrev.add("mod1");
        loader.mods.add(mod4);

        loader.sortMods();

        loader.mods.forEach(System.out::println);
    }

    public static class Loader extends VodkaLoader {
        protected Loader() {
            super(new VodkaClassLoader(new URL[0], Loader.class.getClassLoader()));
        }

        @Override
        public @NotNull MinecraftVersion getMCVersion() {
            return null;
        }

        @Override
        public @NotNull EnvType getEnvironment() {
            return null;
        }

        @Override
        public @NotNull Path getModsDir() {
            return null;
        }

        @Override
        public @NotNull Path getConfigDir() {
            return null;
        }
    }
}
