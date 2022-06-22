package DmN.ICA.vodka.classloader;

import DmN.ICA.vodka.utils.CollectionsHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class VodkaLoaderUtils {
    public static final MethodHandle ClassLoader$defineClass;

    @SuppressWarnings("unchecked")
    public Class<VodkaClassLoader> loadVodkaLoader(ClassLoader loader, boolean inject) throws ClassNotFoundException {
        if (!inject) {
            try {
                ClassNode node = new ClassNode();
                new ClassReader("DmN.ICA.vodka.classloader.VodkaClassLoader").accept(node, 0);

                node.fields.forEach(field -> {
                    for (AnnotationNode annotation : CollectionsHelper.combine(field.visibleAnnotations, field.invisibleAnnotations)) {
                        if (VodkaClassModifier.processDescriptor(annotation.desc).equals("DmN/ICA/vodka/classloader/TransformInject")) {
                            node.fields.remove(field);
                            break;
                        }
                    }
                });

                node.methods.forEach(method -> {
                    for (AnnotationNode annotation : CollectionsHelper.combine(method.visibleAnnotations, method.invisibleAnnotations)) {
                        if (VodkaClassModifier.processDescriptor(annotation.desc).equals("DmN/ICA/vodka/classloader/TransformInject")) {
                            node.methods.remove(method);
                            break;
                        }
                    }
                });

                ClassWriter writer = new ClassWriter(0);
                node.accept(writer);
                byte[] bytes = writer.toByteArray();
                return (Class<VodkaClassLoader>) ClassLoader$defineClass.invoke(loader, bytes, 0, bytes.length);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }

        return (Class<VodkaClassLoader>) loader.loadClass("DmN.ICA.vodka.classloader.VodkaClassLoader");
    }

    static {
        try {
            ClassLoader$defineClass = MethodHandles.lookup().findVirtual(ClassLoader.class, "defineClass", MethodType.methodType(Class.class, byte[].class, int.class, int.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
