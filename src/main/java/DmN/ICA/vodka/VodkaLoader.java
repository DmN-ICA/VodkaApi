package DmN.ICA.vodka;

import DmN.ICA.vodka.api.MinecraftVersion;
import DmN.ICA.vodka.api.VodkaMod;
import DmN.ICA.vodka.impl.EnvType;
import DmN.ICA.vodka.impl.LoaderType;
import org.jetbrains.annotations.NotNull;

public abstract class VodkaLoader implements VodkaMod, VodkaMod.FirstInitializer, VodkaMod.CommonInitializer, VodkaMod.ClientInitializer, VodkaMod.ServerInitializer {
    public static @NotNull VodkaLoader INSTANCE;

    @Override
    public void firstInit() {

    }

    @Override
    public void commonInit() {

    }

    @Override
    public void clientInit() {

    }

    @Override
    public void serverInit() {

    }

    @Override
    public @NotNull String getName() {
        return "Vodka[Loader/Api]";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public abstract @NotNull MinecraftVersion getMCVersion();

    public @NotNull LoaderType getLoaderType() {
        return LoaderType.Unknown;
    }

    public abstract @NotNull EnvType getEnvironment();
}
