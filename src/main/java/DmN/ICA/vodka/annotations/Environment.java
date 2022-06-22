package DmN.ICA.vodka.annotations;

import DmN.ICA.vodka.api.EnvType;

import java.lang.annotation.*;

/**
 * Applied to declare that the annotated element is present only in the specified environment.
 *
 * <p>Use with caution, as Fabric-loader will remove the annotated element in a mismatched environment!</p>
 *
 * <p>When the annotated element is removed, bytecode associated with the element will not be removed.
 * For example, if a field is removed, its initializer code will not, and will cause an error on execution.</p>
 *
 * <p>If an overriding method has this annotation and its overridden method doesn't,
 * unexpected behavior may happen. If an overridden method has this annotation
 * while the overriding method doesn't, it is safe, but the method can be used from
 * the overridden class only in the specified environment.</p>
 *
 * @see EnvironmentInterface
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PACKAGE})
@Documented
public @interface Environment {
    /**
     * @return  the environment type that the annotated element is only present in.
     */
    EnvType value();
}