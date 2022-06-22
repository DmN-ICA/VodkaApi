package DmN.ICA.vodka.test.experiment;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.classloader.VodkaClassModifier;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClassNode node = new VodkaClassModifier(EnvType.SERVER);
        new ClassReader("DmN.ICA.vodka.test.classes.TestMultiClass").accept(node, 0);

        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        Class<?> clazz = new TestClassLoader().defineClass(writer.toByteArray());
        System.out.println(clazz.getMethod("foo").invoke(clazz.newInstance()));
        System.out.println(Arrays.toString(clazz.getInterfaces()));
    }

    public static class TestClassLoader extends URLClassLoader {
        public TestClassLoader() {
            super(new URL[]{});
        }

        public Class<?> defineClass(byte[] bytes) {
            return super.defineClass(bytes, 0, bytes.length);
        }
    }
}
