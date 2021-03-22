package me.threedr3am.guanyu.plugin;

import java.util.Set;
import me.threedr3am.guanyu.core.DenyEvilClassException;
import me.threedr3am.guanyu.core.Plugin;
import me.threedr3am.guanyu.plugin.asm.ASTClassVisitor;
import org.objectweb.asm.ClassReader;

/**
 *
 * 这个插件是直接禁止一些黑名单危险方法的调用，但是，可能会存在误报，需要谨慎使用，特别是agent attach运行时加载检测
 *
 * 默认自带的方法调用黑名单检测插件
 *
 * @author threedr3am
 */
public class BlackMethodDenyPlugin implements Plugin {


    @Override
    public boolean condition(String className) {
        if (DenyMethods.getDenyMethods().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public byte[] check(String className, byte[] byteCode) throws Exception {
        if (!condition(className)) {
            return byteCode;
        }
        ASTClassVisitor astClassVisitor = new ASTClassVisitor(className);
        ClassReader cr = new ClassReader(byteCode);
        cr.accept(astClassVisitor, ClassReader.EXPAND_FRAMES);
        Set<GuanYuMethod> guanYuMethods = astClassVisitor.getMethods();
        for (GuanYuMethod guanYuMethod : guanYuMethods) {
            for (GuanYuMethod denyMethod : DenyMethods.getDenyMethods()) {
                if (denyMethod.match(guanYuMethod) && !denyMethod.white(className)) {
                    throw new DenyEvilClassException(String.format("检测到一个恶意类加载, 它调用了一个危险方法: %s. %s", guanYuMethod.toString(), className));
                }
            }
        }
        return byteCode;
    }
}
