package me.threedr3am.guanyu.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author threedr3am
 */
public class LoadCache {

    private static Map<String, Object> cache = new HashMap<>();

    public static boolean check(String className) {
        return cache.putIfAbsent(className, null) != null;
    }
}
