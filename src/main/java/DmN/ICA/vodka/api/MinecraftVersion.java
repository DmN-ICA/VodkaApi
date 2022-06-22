package DmN.ICA.vodka.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinecraftVersion {
    public final @NotNull String version;

    public MinecraftVersion(@NotNull String version) {
        this.version = version;
    }

    /**
     * @return Up part of MC version
     */
    public int up() {
        return Integer.parseInt(full().split("\\.")[0]);
    }

    /**
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
