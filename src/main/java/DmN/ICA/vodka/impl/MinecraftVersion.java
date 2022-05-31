package DmN.ICA.vodka.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinecraftVersion {
    public final @NotNull String version;

    public MinecraftVersion(@NotNull String version) {
        this.version = version;
    }

    /**
     * Example: [1.7.10] - source version => [7] - up
     * @return Up part of MC version
     */
    public int up() {
        return Integer.parseInt(full().split("\\.")[0]);
    }

    /**
     * Example: [1.7.10] - source version => [1.7.10] - full
     * @return Full MC version
     */
    @NotNull
    public String full() {
        return version;
    }

    public boolean validate(@Nullable MinecraftVersion high, @Nullable MinecraftVersion low) {
        if (high != null && high.up() < this.up())
            return false;
        if (low != null)
            return low.up() <= this.up();
        return true;
    }
}
