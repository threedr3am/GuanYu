package me.threedr3am.guanyu.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @author threedr3am
 */
public class GuanYuClassFileTransformer implements ClassFileTransformer {

    private Instrumentation inst;

    public GuanYuClassFileTransformer(Instrumentation inst) {
        this.inst = inst;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!LoadCache.check(className)) {
            System.out.println(String.format("[GuanYu] 检测到新的类加载: %s", className));
            PluginManager.check(className, classfileBuffer);
        }
        return classfileBuffer;
    }

    public void retransform() {
        Class[] classes = inst.getAllLoadedClasses();
        Set<Class> classSet = new HashSet<>();
        for (Class aClass : classes) {
            if (PluginManager.condition(aClass.getName()) && inst.isModifiableClass(aClass)) {
                classSet.add(aClass);
                System.out.println(String.format("[GuanYu] retransform class: %s", aClass.getName()));
                continue;
            }
        }
        if (!classSet.isEmpty()) {
            try {
                inst.retransformClasses(classSet.toArray(new Class[classSet.size()]));
            } catch (UnmodifiableClassException e) {
                e.printStackTrace();
            }
        }
    }
}
