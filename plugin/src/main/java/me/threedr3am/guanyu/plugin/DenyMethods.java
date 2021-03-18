package me.threedr3am.guanyu.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import me.threedr3am.guanyu.core.Config;

/**
 * @author threedr3am
 */
public class DenyMethods {

    private static Set<GuanYuMethod> denyMethods;

    private static void init() {
        denyMethods = new HashSet<>();
        try {
            System.out.println("[GuanYu] 读取方法调用黑名单配置文件 ...");
            Scanner scanner = new Scanner(new FileInputStream(Config.getInstance().getDenyMethodsConfigFile()));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] items = line.split(" ");
                denyMethods.add(new GuanYuMethod(items[0], items[1], items[2], items.length > 3 ? items[3] : ""));
            }
        } catch (FileNotFoundException e) {
            System.err.println("[GuanYu] 无法找到方法调用黑名单配置文件，请通过参数denyMethodsConfigFile配置");
        }
    }

    public static Set<GuanYuMethod> getDenyMethods() {
        if (denyMethods == null) {
            synchronized (DenyMethods.class) {
                if (denyMethods == null) {
                    init();
                }
            }
        }
        return denyMethods;
    }
}
