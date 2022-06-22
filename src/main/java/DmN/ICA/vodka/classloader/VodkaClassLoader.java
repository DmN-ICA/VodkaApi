package DmN.ICA.vodka.classloader;

import DmN.ICA.vodka.api.EnvType;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VodkaClassLoader extends URLClassLoader {
    public VodkaClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static VodkaClassLoader create(File modsDir, ClassLoader parent) throws IOException {
        return new VodkaClassLoader(buildModsDir(modsDir), parent);
    }

    public static URL[] buildModsDir(File modsDir) throws IOException {
        if (!modsDir.exists())
            if (!modsDir.mkdirs())
                throw new IOException("File creation error! File `" + modsDir + "` impossible to create!");
        File[] files = modsDir.listFiles();
        URL[] mods = new URL[Objects.requireNonNull(files).length];
        for (int i = 0; i < files.length; i++)
            mods[i] = files[i].toURI().toURL();
        return mods;
    }

    public byte @NotNull [] transform(@NotNull EnvType envType, @NotNull String name, byte @NotNull [] bytes) {
        ClassNode node = new VodkaClassModifier(envType);
        new ClassReader(bytes).accept(node, 0);
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    // TRANSFORM INJECTOR //

    public final List<transformPPFunction> preTransformerFunctions = new ArrayList<>();
    public final List<transformPPFunction> postTransformerFunctions = new ArrayList<>();

    public void preTransform(@NotNull EnvType envType, @NotNull String name, byte @NotNull [] bytes, @NotNull ClassNode node) {
        preTransformerFunctions.forEach(transformPPFunction -> transformPPFunction.ppTransform(envType, name, bytes, node));
    }

    public void postTransform(@NotNull EnvType envType, @NotNull String name, byte @NotNull [] bytes, @NotNull ClassNode node) {
        postTransformerFunctions.forEach(transformPPFunction -> transformPPFunction.ppTransform(envType, name, bytes, node));
    }

    @FunctionalInterface
    public interface transformPPFunction {
        void ppTransform(@NotNull EnvType envType, @NotNull String name, byte @NotNull [] bytes, @NotNull ClassNode node);
    }
}
