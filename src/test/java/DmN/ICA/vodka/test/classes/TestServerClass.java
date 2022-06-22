package DmN.ICA.vodka.test.classes;

import DmN.ICA.vodka.annotations.Environment;
import DmN.ICA.vodka.api.EnvType;

@Environment(EnvType.SERVER)
public class TestServerClass {
    public interface TestServerInterface {
        default int server() {
            return 33;
        }
    }
}
