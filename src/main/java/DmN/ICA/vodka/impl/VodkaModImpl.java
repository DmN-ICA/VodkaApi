package DmN.ICA.vodka.impl;

import DmN.ICA.vodka.api.MinecraftVersion;
import DmN.ICA.vodka.api.VodkaMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VodkaModImpl implements VodkaMod {
    public @NotNull String id;
    public int version;
    public @NotNull String name;
    public @Nullable String description;
    public @NotNull String[] authors;
    public @Nullable MinecraftVersion highMCVersion;
    public @Nullable MinecraftVersion lowMCVersion;
    public @Nullable String[] loadPost;
    public @Nullable String[] loadPrev;
    public @Nullable String[] dependencies;
    public @Nullable String modClass;
    public @Nullable Object instance;
    public @NotNull Type type;

    @Override
    public @NotNull String id() {
        return id;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @Nullable String description() {
        return description;
    }

    @Override
    public @NotNull String[] authors() {
        return authors;
    }

    @Override
    public @Nullable MinecraftVersion highMCVersion() {
        return highMCVersion;
    }

    @Override
    public @Nullable MinecraftVersion lowMCVersion() {
        return lowMCVersion;
    }

    @Override
    public @Nullable String[] loadPost() {
        return loadPost;
    }

    @Override
    public @Nullable String[] loadPrev() {
        return loadPrev;
    }

    @Override
    public @Nullable String[] dependencies() {
        return dependencies;
    }

    @Override
    public @Nullable String modClass() {
        return modClass;
    }

    @Override
    public @Nullable Object instance() {
        return instance;
    }

    @Override
    public @NotNull Type type() {
        return type;
    }
}
