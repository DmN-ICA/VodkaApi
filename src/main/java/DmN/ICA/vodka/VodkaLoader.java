package DmN.ICA.vodka;

import DmN.ICA.vodka.api.MinecraftVersion;
import DmN.ICA.vodka.api.VodkaMod;
import DmN.ICA.vodka.impl.EnvType;
import DmN.ICA.vodka.impl.LoaderType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public abstract class VodkaLoader implements VodkaMod, VodkaMod.FirstInitializer, VodkaMod.CommonInitializer, VodkaMod.ClientInitializer, VodkaMod.ServerInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Vodka[Loader/Api]");
    public static @NotNull VodkaLoader INSTANCE;

    @Override
    public void firstInit() {
        LOGGER.info("Called FirstInit");
    }

    @Override
    public void commonInit() {
        LOGGER.info("Called CommonInit");
    }

    @Override
    public void clientInit() {
        LOGGER.info("Called ClientInit");
    }

    @Override
    public void serverInit() {
        LOGGER.info("Called ServerInit");
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
