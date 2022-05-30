package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VodkaMod {
    @NotNull
    String name();

    @NotNull
    String version();

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

    @Nullable
    String modClass();

    @Nullable
    Object instance();

    boolean needCreateInstance();


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
