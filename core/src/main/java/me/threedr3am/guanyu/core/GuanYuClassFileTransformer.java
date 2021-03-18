package me.threedr3am.guanyu.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author threedr3am
 */
public class GuanYuClassFileTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!LoadCache.check(className)) {
            System.out.println(String.format("[GuanYu] 检测到新的类加载: %s", className));
            PluginManager.check(className, classfileBuffer);
        }
        return classfileBuffer;
    }
}
