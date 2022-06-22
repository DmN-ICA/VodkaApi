package DmN.ICA.vodka.test;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.exception.EnvTypeException;
import org.junit.Test;

public class EnvClientTest {
    @Test
    public void test0() throws Exception {
        try (var loader = new TestClassLoader(EnvClientTest.class.getClassLoader())) {
            assert loader.testLoad("DmN.ICA.vodka.test.classes.TestClientClass", EnvType.CLIENT) != null;
            loader.testLoad("DmN.ICA.vodka.test.classes.TestServerClass", EnvType.CLIENT);
            throw new RuntimeException("Invalid env load!");
        } catch (EnvTypeException ignored) {
        }
    }

    @Test
    public void test1() throws Exception {
        try (var loader = new TestClassLoader(EnvClientTest.class.getClassLoader())) {
            var clazz = loader.testLoad("DmN.ICA.vodka.test.classes.TestMultiClass", EnvType.CLIENT);
            assert clazz  != null;
            assert clazz.getMethods().length - Object.class.getMethods().length == 3;
            assert clazz.getMethod("foo").invoke(clazz.newInstance()).equals("client!");
        }
    }

    @Test
    public void test2() throws Exception {
        try (var loader = new TestClassLoader(EnvClientTest.class.getClassLoader())) {
            var clazz = loader.testLoad("DmN.ICA.vodka.test.classes.TestMultiClass", EnvType.CLIENT);
            assert clazz  != null;
            assert clazz.getInterfaces().length == 2;
            assert clazz.getInterfaces()[0].equals(loader.loadClass("DmN.ICA.vodka.test.classes.TestClientClass$TestClientInterface0"));
            assert clazz.getInterfaces()[1].equals(loader.loadClass("DmN.ICA.vodka.test.classes.TestClientClass$TestClientInterface1"));
        }
    }
}
