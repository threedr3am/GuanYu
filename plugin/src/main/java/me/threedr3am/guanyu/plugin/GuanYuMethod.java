package me.threedr3am.guanyu.plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author threedr3am
 */
public class GuanYuMethod {

    private String className;
    private String method;
    private String desc;
    private Set<String> whiteClassNames;

    public GuanYuMethod(String className, String method, String desc,
        String whiteClassNames) {
        this.className = className;
        this.method = method;
        this.desc = desc;
        this.whiteClassNames = new HashSet<>(Arrays.asList(whiteClassNames.split(",")));
    }

    public String getClassName() {
        return className;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public boolean match(GuanYuMethod target) {
        if (!className.equals(target.getClassName())) {
            return false;
        }
        if (!(Objects.equals(method, "*") || Objects.equals(method, target.getMethod()))) {
            return false;
        }
        if (!(Objects.equals(desc, "*") || Objects.equals(desc, target.getDesc()))) {
            return false;
        }
        return true;
    }

    public boolean white(String className) {
        return whiteClassNames.contains(className);
    }

    @Override
    public String toString() {
        return "{" +
            "className='" + className + '\'' +
            ", method='" + method + '\'' +
            ", desc='" + desc + '\'' +
            '}';
    }
}
