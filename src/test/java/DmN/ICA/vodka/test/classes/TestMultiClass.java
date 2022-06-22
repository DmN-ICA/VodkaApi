package DmN.ICA.vodka.test.classes;

import DmN.ICA.vodka.annotations.EnvironmentInterface;
import DmN.ICA.vodka.annotations.EnvironmentInterfaces;
import DmN.ICA.vodka.annotations.EnvironmentMethod;
import DmN.ICA.vodka.api.EnvType;

@EnvironmentInterface(value = EnvType.CLIENT, itf = TestClientClass.TestClientInterface0.class)
@EnvironmentInterfaces({ @EnvironmentInterface(value = EnvType.CLIENT, itf = TestClientClass.TestClientInterface1.class), @EnvironmentInterface(value = EnvType.SERVER, itf = TestServerClass.TestServerInterface.class) })
public class TestMultiClass implements TestClientClass.TestClientInterface0, TestClientClass.TestClientInterface1, TestServerClass.TestServerInterface {
    @EnvironmentMethod(client = "fooC()Ljava/lang/String;", server = "fooS()Ljava/lang/String;")
    public String foo() {
        return "foo!";
    }

    public String fooC() {
        return "client!";
    }

    public String fooS() {
        return "server!";
    }
}
