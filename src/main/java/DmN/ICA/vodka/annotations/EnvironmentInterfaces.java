package DmN.ICA.vodka.annotations;

import java.lang.annotation.*;

/**
 * A container of multiple {@link EnvironmentInterface} annotations on a class, often defined implicitly.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface EnvironmentInterfaces {
    /**
     * @return the {@link EnvironmentInterface} annotations it holds.
     */
    EnvironmentInterface[] value();
}