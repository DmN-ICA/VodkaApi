package DmN.ICA.vodka.classloader;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.exception.EnvTypeException;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class VodkaClassModifier extends ClassNode {
    public final EnvType env;
    public final List<MethodInfoVisitor> allMethods = new ArrayList<>();
    public final Map<String, AnnotationNode> annotations = new HashMap<>();

    public VodkaClassModifier(EnvType env) {
        super(ASM9);
        this.env = env;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationNode node = (AnnotationNode) super.visitAnnotation(descriptor, visible);
        this.annotations.put(processDescriptor(descriptor), node);
        return node;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void visitEnd() {
        super.visitEnd();
        annotations.forEach((annotationClass, annotation) -> {
            Map<String, Object> data = wrapAnnData(annotation.values);
            switch (annotationClass) {
                case "DmN/ICA/vodka/annotations/Environment":
                    if (data.get("value") != env)
                        throw new EnvTypeException("The class `" + this.name + "` only for " + data.get("value") + '!');
                    break;
                case "DmN/ICA/vodka/annotations/EnvironmentInterface":
                    if (!((String[]) data.get("value"))[1].equals(env.name()))
                        this.interfaces.remove(processDescriptor(((Type) data.get("itf")).getDescriptor()));
                    break;
                case "DmN/ICA/vodka/annotations/EnvironmentInterfaces":
                    for (AnnotationNode itf : ((List<AnnotationNode>) data.get("value")))
                        if (!((String[]) itf.values.get(1))[1].equals(env.name()))
                            this.interfaces.remove(processDescriptor(((Type) itf.values.get(3)).getDescriptor()));
                    break;
            }
        });
        allMethods.forEach(method -> {
            for (String key : method.annotations.keySet()) {
                if (!key.startsWith("DmN/ICA/vodka/annotations"))
                    return;
                Map<String, Object> parameters = wrapAnnData(method.annotations.get(key).values);
                if (key.equals("DmN/ICA/vodka/annotations/Environment") && parameters.get("value") != env) {
                    deleteMethod(method.name, method.descriptor);
                    break;
                } else if (key.equals("DmN/ICA/vodka/annotations/EnvironmentMethod")) {
                    MethodInfoVisitor client = allMethods.stream().filter(m -> (m.name + m.descriptor).equals(parameters.get("client"))).findFirst().orElse(null);
                    MethodInfoVisitor server = allMethods.stream().filter(m -> (m.name + m.descriptor).equals(parameters.get("server"))).findFirst().orElse(null);
                    deleteMethod(method.name, method.descriptor);
                    if (env == EnvType.CLIENT) {
                        if (client == null)
                            throw new NullPointerException("Client method is null!");
                        findMethod(client.name, client.descriptor).orElseThrow(NullPointerException::new).name = method.name;
                        if (server != null)
                            deleteMethod(server.name, server.descriptor);
                    } else {
                        if (server == null)
                            throw new NullPointerException("Server method is null!");
                        findMethod(server.name, server.descriptor).orElseThrow(NullPointerException::new).name = method.name;
                        if (client != null)
                            deleteMethod(client.name, client.descriptor);
                    }
                }
            }
        });
    }

    public Optional<MethodNode> findMethod(String name, String descriptor) {
        return this.methods.stream().filter(method -> method.name.equals(name) && method.desc.equals(descriptor)).findFirst();
    }

    public void deleteMethod(String name, String descriptor) {
        findMethod(name, descriptor).ifPresent(this.methods::remove);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodInfoVisitor visitor = new MethodInfoVisitor(super.visitMethod(access, name, descriptor, signature, exceptions), name, descriptor);
        this.allMethods.add(visitor);
        return visitor;
    }

    public static String processDescriptor(String descriptor) {
        return descriptor.substring(1, descriptor.length() - 1);
    }

    public Map<String, Object> wrapAnnData(List<Object> data) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < data.size(); i += 2)
            map.put((String) data.get(i), data.get(i + 1));
        return map;
    }

    public static class MethodInfoVisitor extends MethodVisitor {
        public final String name;
        public final String descriptor;
        public final Map<String,AnnotationNode> annotations = new HashMap<>();

        public MethodInfoVisitor(MethodVisitor methodVisitor, String name, String descriptor) {
            super(ASM9, methodVisitor);
            this.name = name;
            this.descriptor = descriptor;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            AnnotationNode node = (AnnotationNode) super.visitAnnotation(descriptor, visible);
            this.annotations.put(processDescriptor(descriptor), node);
            return node;
        }
    }
}
