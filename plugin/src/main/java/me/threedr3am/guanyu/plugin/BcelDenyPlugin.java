package me.threedr3am.guanyu.plugin;

import java.util.Set;
import me.threedr3am.guanyu.core.DenyEvilClassException;
import me.threedr3am.guanyu.core.Plugin;
import me.threedr3am.guanyu.plugin.asm.ASTClassVisitor;
import org.objectweb.asm.ClassReader;

/**
 * @author threedr3am
 */
public class BcelDenyPlugin implements Plugin {

    @Override
    public byte[] check(String className, byte[] byteCode) throws Exception {
        if (className.startsWith("$$BCEL$$")) {
            if (DenyMethods.getDenyMethods().isEmpty()) {
                return byteCode;
            }
            ASTClassVisitor astClassVisitor = new ASTClassVisitor(className);
            ClassReader cr = new ClassReader(byteCode);
            cr.accept(astClassVisitor, ClassReader.EXPAND_FRAMES);
            Set<GuanYuMethod> guanYuMethods = astClassVisitor.getMethods();
            for (GuanYuMethod guanYuMethod : guanYuMethods) {
                for (GuanYuMethod denyMethod : DenyMethods.getDenyMethods()) {
                    if (denyMethod.match(guanYuMethod) && !denyMethod.white(className)) {
                        throw new DenyEvilClassException(String.format("检测到一个不安全的BCEL字节码加载. 它调用了一个危险方法: %s. %s", guanYuMethod.toString(), className));
                    }
                }
            }
        }
        return byteCode;
    }
}
