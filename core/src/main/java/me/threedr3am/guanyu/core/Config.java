package me.threedr3am.guanyu.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jingfeng
 */
public class Config {

    private String denyMethodsConfigFile;

    private static Config config;

    public static final Config getInstance() {
        if (config == null) {
            synchronized (Config.class) {
                if (config == null) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    public static void init(String action) throws IllegalAccessException {
        Config config = getInstance();
        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = Config.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("config")) {
                continue;
            }
            fieldMap.put(field.getName(), field);
        }

        Pattern pattern = Pattern.compile("((.+?)=(.+?))(,|$)");
        Matcher matcher = pattern.matcher(action);
        while (matcher.find()) {
            String key = matcher.group(2);
            String value = matcher.group(3);
            Field field;
            if ((field = fieldMap.get(key)) != null) {
                field.set(config, value);
            }
        }
    }

    public String getDenyMethodsConfigFile() {
        return denyMethodsConfigFile;
    }

    public static void main(String[] args) throws IllegalAccessException {
        init("denyMethodsConfigFile=/Users/threedr3am/git-project/GuanYu/conf/deny.conf");
    }
}
