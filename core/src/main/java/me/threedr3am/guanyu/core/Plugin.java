package me.threedr3am.guanyu.core;

/**
 * @author threedr3am
 */
public interface Plugin {

    boolean condition(String className);

    byte[] check(String className, byte[] byteCode) throws Exception;
}
