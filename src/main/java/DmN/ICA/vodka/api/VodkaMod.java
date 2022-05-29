package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VodkaMod {
    @NotNull
    String getName();

    @NotNull
    String getVersion();

    @Nullable
    default MinecraftVersion getHighMCVersion() {
        return null;
    }

    @Nullable
    default MinecraftVersion getLowMCVersion() {
        return null;
    }

    @NotNull
    default String[] loadPost() {
        return new String[]{};
    }

    @NotNull
    default String[] loadPrev() {
        return new String[]{};
    }

    @NotNull
    default String[] needLoad() {
        return new String[]{};
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
