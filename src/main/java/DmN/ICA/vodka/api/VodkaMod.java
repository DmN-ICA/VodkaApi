package DmN.ICA.vodka.api;

import DmN.ICA.vodka.api.MinecraftVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface VodkaMod {

    // FOR LOAD

    @NotNull String id();

    int version();

    @NotNull Type type();

    @Nullable String modClass();

    @Nullable Object instance();

    // INFO

    @NotNull String name();

    @Nullable String description();

    @NotNull List<String> authors();

    // OPTIONAL

    @Nullable MinecraftVersion highMCVersion();

    @Nullable MinecraftVersion lowMCVersion();

    @NotNull List<String> loadPost();

    @NotNull List<String> loadPrev();

    @NotNull List<String> dependencies();

    enum Type {
        MOD,
        LIBRARY
    }

    @FunctionalInterface
    interface FirstInitializer {
        void firstInit();
    }

    @FunctionalInterface
    interface ClientInitializer {
        void clientInit();
    }

    @FunctionalInterface
    interface CommonInitializer {
        void commonInit();
    }

    @FunctionalInterface
    interface ServerInitializer {
        void serverInit();
    }
}
