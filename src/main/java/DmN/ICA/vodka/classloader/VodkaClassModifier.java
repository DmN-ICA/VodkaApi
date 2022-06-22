package DmN.ICA.vodka.classloader;

import DmN.ICA.vodka.api.EnvType;
import DmN.ICA.vodka.exception.EnvTypeException;
import DmN.ICA.vodka.utils.CollectionsHelper;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class VodkaClassModifier extends ClassNode {
    public final EnvType env;

    public VodkaClassModifier(EnvType env) {
        super(ASM9);
        this.env = env;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void visitEnd() {
        super.visitEnd();

        CollectionsHelper.combine(this.visibleAnnotations, this.invisibleAnnotations).forEach(annotation -> {
            Map<String, Object> data = wrapAnnData(annotation.values);
            switch (processDescriptor(annotation.desc)) {
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

        methods:
        for (MethodNode method : this.methods) {
            for (AnnotationNode annotation : CollectionsHelper.combine(method.visibleAnnotations, method.invisibleAnnotations)) {
                Map<String, Object> parameters = wrapAnnData(annotation.values);
                String aclass = processDescriptor(annotation.desc);
                if (aclass.equals("DmN/ICA/vodka/annotations/Environment") && parameters.get("value") != env) {
                    this.methods.remove(method);
                    break methods;
                } else if (aclass.equals("DmN/ICA/vodka/annotations/EnvironmentMethod")) {
                    MethodNode client = this.methods.stream().filter(m -> (m.name + m.desc).equals(parameters.get("client"))).findFirst().orElse(null);
                    MethodNode server = this.methods.stream().filter(m -> (m.name + m.desc).equals(parameters.get("server"))).findFirst().orElse(null);
                    this.methods.remove(method);
                    if (env == EnvType.CLIENT) {
                        if (client == null)
                            throw new NullPointerException("Client method is null!");
                        findMethod(client.name, client.desc).orElseThrow(NullPointerException::new).name = method.name;
                        this.methods.remove(server);
                    } else {
                        if (server == null)
                            throw new NullPointerException("Server method is null!");
                        findMethod(server.name, server.desc).orElseThrow(NullPointerException::new).name = method.name;
                        this.methods.remove(client);
                    }
                }
            }
        }
    }

    public Optional<MethodNode> findMethod(String name, String descriptor) {
        return this.methods.stream().filter(method -> method.name.equals(name) && method.desc.equals(descriptor)).findFirst();
    }

    public static String processDescriptor(String descriptor) {
        return descriptor.substring(1, descriptor.length() - 1);
    }

    public Map<String, Object> wrapAnnData(List<Object> data) {
        Map<String, Object> map = new HashMap<>();
        if (data != null)
            for (int i = 0; i < data.size(); i += 2)
                map.put((String) data.get(i), data.get(i + 1));
        return map;
    }
}
