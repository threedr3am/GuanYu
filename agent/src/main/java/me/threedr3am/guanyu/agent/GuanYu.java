package me.threedr3am.guanyu.agent;

import java.lang.instrument.Instrumentation;
import me.threedr3am.guanyu.core.Config;
import me.threedr3am.guanyu.core.GuanYuClassFileTransformer;

/**
 * @author threedr3am
 */
public class GuanYu {

    public static void premain(String agentArg, Instrumentation inst) {
        init(agentArg, inst);
    }

    public static void agentmain(String agentArg, Instrumentation inst) {
        init(agentArg, inst);
    }

    public static synchronized void init(String action, Instrumentation inst) {
        System.out.println("[GuanYu] 类加载监控Agent启动 ...");
        System.out.println(String.format("[GuanYu] 参数: %s", action));
        try {
            Config.init(action);
            inst.addTransformer(new GuanYuClassFileTransformer(), true);
        } catch (Throwable e) {
            System.err.println("[GuanYu] 类加载监控Agent初始化失败，将会失去安全保护。");
            e.printStackTrace();
        }
    }
}
