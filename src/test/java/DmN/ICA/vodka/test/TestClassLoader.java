package DmN.ICA.vodka.test;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.classloader.VodkaClassLoader;

import java.net.URL;

public class TestClassLoader extends VodkaClassLoader {
    public TestClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
    }

    public Class<?> testLoad(String name, EnvType env) throws Exception {
        byte[] bytes = transform(env, name, this.getResourceAsStream(name.replace('.', '/') + ".class").readAllBytes());
        return this.defineClass(name, bytes, 0, bytes.length);
    }
}
