package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VodkaMod {

    // FOR LOAD

    @NotNull
    String id();

    @NotNull
    String version();

    @NotNull
    Type type();

    @Nullable
    String modClass();

    @Nullable
    Object instance();

    // INFO

    @NotNull
    String name();

    @Nullable
    String description();

    @NotNull
    String[] authors();

    // OPTIONAL

    @Nullable
    MinecraftVersion highMCVersion();

    @Nullable
    MinecraftVersion lowMCVersion();

    @Nullable
    String[] loadPost();

    @Nullable
    String[] loadPrev();

    @Nullable
    String[] dependencies();

    enum Type {
        MOD,
        LIBRARY
    }


    interface FirstInitializer {
        void firstInit();
    }

    interface ClientInitializer {
        void clientInit();
    }

    interface CommonInitializer {
        void commonInit();
    }

    interface ServerInitializer {
        void serverInit();
    }
}
