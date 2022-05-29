package DmN.ICA.vodka.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class VodkaClassLoader extends URLClassLoader {
    public VodkaClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static VodkaClassLoader create(File modsDir, ClassLoader parent) throws MalformedURLException {
        if (!modsDir.exists())
            modsDir.mkdirs();
        File[] files = modsDir.listFiles();
        URL[] mods = new URL[files.length];
        for (int i = 0; i < files.length; i++)
            mods[i] = files[i].toURL();
        return new VodkaClassLoader(mods, parent);
    }
}
