package me.threedr3am.guanyu.plugin.asm;

import java.util.HashSet;
import java.util.Set;
import me.threedr3am.guanyu.plugin.GuanYuMethod;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author threedr3am
 */
public class ASTClassVisitor extends ClassVisitor {

    private String className;
    private Set<GuanYuMethod> methods;

    public ASTClassVisitor(String className) {
        super(Opcodes.ASM6);
        this.className = className;
        this.methods = new HashSet<>();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
        String[] exceptions) {
        return new ASTMethodVisitor(className, methods);
    }

    public Set<GuanYuMethod> getMethods() {
        return methods;
    }
}
