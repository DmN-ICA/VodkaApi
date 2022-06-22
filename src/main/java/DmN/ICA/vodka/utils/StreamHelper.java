package DmN.ICA.vodka.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class StreamHelper {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] readAllBytesAndClose(InputStream is) throws IOException {
        Objects.requireNonNull(is);
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, is.available());
        is.close();
        return bytes;
    }
}
