package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MinecraftVersion {
    /**
     * Example: [1.7.10] - source version => [7] - up
     * @return Up part of MC version
     */
    default int up() {
        return Integer.parseInt(full().split("\\.")[0]);
    }

    /**
     * Example: [1.7.10] - source version => [1.7.10] - full
     * @return Full MC version
     */
    @NotNull
    String full();

    default boolean validate(@Nullable MinecraftVersion high, @Nullable MinecraftVersion low) {
        if (high != null && high.up() < this.up())
            return false;
        if (low != null)
            return low.up() <= this.up();
        return true;
    }

    class MinecraftVersionImpl {
        public final String version;

        public MinecraftVersionImpl(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }
}
