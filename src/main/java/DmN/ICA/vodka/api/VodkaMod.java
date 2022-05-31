package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface VodkaMod {

    // FOR LOAD

    @NotNull
    String id();

    int version();

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
    List<String> authors();

    // OPTIONAL

    @Nullable
    MinecraftVersion highMCVersion();

    @Nullable
    MinecraftVersion lowMCVersion();

    @NotNull
    List<String> loadPost();

    @NotNull
    List<String> loadPrev();

    @NotNull
    List<String> dependencies();

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
