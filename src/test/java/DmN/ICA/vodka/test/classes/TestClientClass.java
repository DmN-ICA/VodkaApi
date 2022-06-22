package DmN.ICA.vodka.test.classes;

import DmN.ICA.vodka.annotations.Environment;
import DmN.ICA.vodka.api.EnvType;

@Environment(EnvType.CLIENT)
public class TestClientClass {
    public interface TestClientInterface0 {
        default int client0() {
            return 12;
        }
    }

    public interface TestClientInterface1 {
        default int client1() {
            return 21;
        }
    }
}
