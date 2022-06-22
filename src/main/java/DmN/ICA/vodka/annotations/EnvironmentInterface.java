package DmN.ICA.vodka.annotations;

import DmN.ICA.vodka.api.EnvType;

import java.lang.annotation.*;

/**
 * Applied to declare that a class implements an interface only in the specified environment.
 *
 * <p>Use with caution, as Fabric-loader will remove the interface from {@code implements} declaration
 * of the class in a mismatched environment!</p>
 *
 * <p>Implemented methods are not removed. To remove implemented methods, use {@link Environment}.</p>
 *
 * @see Environment
 */
@Retention(RetentionPolicy.CLASS)
@Repeatable(EnvironmentInterfaces.class)
@Target(ElementType.TYPE)
@Documented
public @interface EnvironmentInterface {
    /**
     * @return  the environment type that the specific interface is only implemented in.
     */
    EnvType value();

    /**
     * @return  the interface class.
     */
    Class<?> itf();
}
