package DmN.ICA.vodka.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeHelper {
    public static final Unsafe unsafe = getUnsafe();

    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
