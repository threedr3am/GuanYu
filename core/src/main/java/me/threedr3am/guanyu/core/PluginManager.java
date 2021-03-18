package me.threedr3am.guanyu.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author threedr3am
 */
public class PluginManager {

    private static List<Plugin> pluginList = new ArrayList<>();

    static {
        ServiceLoader<Plugin> pluginServiceLoader = ServiceLoader.load(Plugin.class);
        Iterator<Plugin> pluginIterator = pluginServiceLoader.iterator();
        while (pluginIterator.hasNext()) {
            Plugin plugin = pluginIterator.next();
            pluginList.add(plugin);
            System.out.println(String.format("[GuanYu] 加载插件: %s", plugin.getClass().toString()));
        }
        System.out.println(String.format("[GuanYu] 加载插件数量: %d", pluginList.size()));
    }

    public static byte[] check(String className, byte[] byteCode) {
        for (Plugin plugin : pluginList) {
            try {
                plugin.check(className, byteCode);
            } catch (DenyEvilClassException e) {
                System.err.println(e.getMessage());
                return new byte[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return byteCode;
    }
}
