package DmN.ICA.vodka.api;

import DmN.ICA.vodka.annotations.Environment;
import DmN.ICA.vodka.annotations.EnvironmentInterface;
import DmN.ICA.vodka.annotations.EnvironmentInterfaces;
import DmN.ICA.vodka.annotations.EnvironmentMethod;
import javassist.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class VodkaClassLoader extends URLClassLoader {
    public VodkaClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static VodkaClassLoader create(File modsDir, ClassLoader parent) throws MalformedURLException {
        return new VodkaClassLoader(buildModsDir(modsDir), parent);
    }

    public static URL[] buildModsDir(File modsDir) throws MalformedURLException {
        if (!modsDir.exists())
            modsDir.mkdirs();
        File[] files = modsDir.listFiles();
        URL[] mods = new URL[files.length];
        for (int i = 0; i < files.length; i++)
            mods[i] = files[i].toURL();
        return mods;
    }

    public byte[] transform(EnvType envType, String name, byte[] bytes) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new ByteArrayClassPath(name, bytes));
        CtClass clazz = pool.getCtClass(name);

        if (clazz.hasAnnotation(Environment.class)) {
            Object type = ((Environment) clazz.getAnnotation(Environment.class)).value();
            if (type != envType)
                throw new ClassNotFoundException("Class " + name + " loaded only in " + type + "'s!");
        }

        if (clazz.hasAnnotation(EnvironmentInterface.class)) {
            EnvironmentInterface annotation = ((EnvironmentInterface) clazz.getAnnotation(EnvironmentInterface.class));
            if (annotation.value() != envType) {
                String filter = annotation.itf().getName();
                clazz.setInterfaces(Arrays.stream(clazz.getInterfaces()).filter(inf -> !inf.getName().equals(filter)).toArray(CtClass[]::new));
            }
        }

        if (clazz.hasAnnotation(EnvironmentInterfaces.class)) {
            EnvironmentInterfaces annotation = (EnvironmentInterfaces) clazz.getAnnotation(EnvironmentInterfaces.class);
            EnvironmentInterface[] interfaces = Arrays.stream(annotation.value()).filter(inf -> inf.value() != envType).toArray(EnvironmentInterface[]::new);
            clazz.setInterfaces(Arrays.stream(clazz.getInterfaces()).filter(inf -> !Arrays.stream(interfaces).allMatch(filter -> filter.itf().getName().equals(inf.getName()))).toArray(CtClass[]::new));
        }

        filterMember(envType, clazz.getDeclaredFields(), clazz::removeField);
        filterMember(envType, clazz.getDeclaredMethods(), clazz::removeMethod);
        filterMember(envType, clazz.getConstructors(), clazz::removeConstructor);

        for (CtMethod method : clazz.getDeclaredMethods()) {
            if (method.hasAnnotation(EnvironmentMethod.class)) {
                EnvironmentMethod annotation = (EnvironmentMethod) method.getAnnotation(EnvironmentMethod.class);
                getMethod(clazz, envType == EnvType.CLIENT ? annotation.client() : annotation.server()).setName(method.getName());
                clazz.removeMethod(getMethod(clazz, envType == EnvType.CLIENT ? annotation.server() : annotation.client()));
                clazz.removeMethod(method);
            }
        }

        return clazz.toBytecode();
    }

    public CtMethod getMethod(CtClass clazz, String info) throws NotFoundException {
        String[] pinfo = info.split("\\(");
        return clazz.getMethod(pinfo[0], '(' + pinfo[1]);
    }

    public <T extends CtMember> void filterMember(EnvType envType, T[] members, FilterAction<T> consumer) throws Exception {
        for (T member : members)
            if (member.hasAnnotation(Environment.class) &&  ((Environment) member.getAnnotation(Environment.class)).value() != envType)
                consumer.filter(member);
    }

    @FunctionalInterface
    public interface FilterAction <T extends CtMember> {
        void filter(T member) throws Exception;
    }
}
