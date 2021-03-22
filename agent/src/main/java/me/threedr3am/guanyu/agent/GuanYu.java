package me.threedr3am.guanyu.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import java.io.IOException;
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
            GuanYuClassFileTransformer guanYuClassFileTransformer = new GuanYuClassFileTransformer(inst);
            inst.addTransformer(guanYuClassFileTransformer, true);
            guanYuClassFileTransformer.retransform();
        } catch (Throwable e) {
            System.err.println("[GuanYu] 类加载监控Agent初始化失败，将会失去安全保护。");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
        throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (args.length == 0) {
            System.err.println("参数缺少，例：java -jar GuanYu.jar 23232 denyMethodsConfigFile=/tmp/deny.conf，23232为需要attach的jvm进程号，denyMethodsConfigFile=/tmp/deny.conf为黑名单方法配置文件路径配置项！");
            System.exit(-1);
        }
        VirtualMachine vmObj = null;

        try {
            vmObj = VirtualMachine.attach(args[0]);
            String agentpath = GuanYu.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            if (vmObj != null) {
                vmObj.loadAgent(agentpath, args[1]);
            }
        } finally {
            if (null != vmObj) {
                vmObj.detach();
            }

        }
    }
}
