package DmN.ICA.vodka.impl;

import DmN.ICA.vodka.VodkaLoader;
import DmN.ICA.vodka.api.VodkaMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VodkaModImpl implements VodkaMod {
    public @NotNull String id;
    public int version;
    public @NotNull String name;
    public @Nullable String description;
    public @NotNull List<String> authors;
    public @Nullable MinecraftVersion highMCVersion;
    public @Nullable MinecraftVersion lowMCVersion;
    public @NotNull List<String> loadPost;
    public @NotNull List<String> loadPrev;
    public @NotNull List<String> dependencies;
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
    public @NotNull List<String> authors() {
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
    public @NotNull List<String> loadPost() {
        return loadPost;
    }

    @Override
    public @NotNull List<String> loadPrev() {
        return loadPrev;
    }

    @Override
    public @NotNull List<String> dependencies() {
        return dependencies;
    }

    @Override
    public @Nullable String modClass() {
        return modClass;
    }

    @Override
    public @Nullable Object instance() {
        try {
            if (instance == null && modClass != null)
                instance = Class.forName(modClass).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    @Override
    public @NotNull Type type() {
        return type;
    }
}
