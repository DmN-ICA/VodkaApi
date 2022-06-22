package DmN.ICA.vodka.test;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.classloader.VodkaClassLoader;
import DmN.ICA.vodka.utils.StreamHelper;

import java.net.URL;

public class TestClassLoader extends VodkaClassLoader {
    public TestClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
    }

    public Class<?> testLoad(String name, EnvType env) throws Exception {
        byte[] bytes = transform(env, name, StreamHelper.readAllBytesAndClose(this.getResourceAsStream(name.replace('.', '/') + ".class")));
        return this.defineClass(name, bytes, 0, bytes.length);
    }
}
