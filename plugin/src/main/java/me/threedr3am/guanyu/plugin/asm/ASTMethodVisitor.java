package me.threedr3am.guanyu.plugin.asm;

import java.util.Set;
import me.threedr3am.guanyu.plugin.GuanYuMethod;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author threedr3am
 */
public class ASTMethodVisitor extends MethodVisitor {

    private String className;
    private Set<GuanYuMethod> methods;

    public ASTMethodVisitor(String className, Set<GuanYuMethod> methods) {
        super(Opcodes.ASM8);
        this.className = className;
        this.methods = methods;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor,
        boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        methods.add(new GuanYuMethod(owner, name, descriptor, ""));
    }
}
