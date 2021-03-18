# GuanYu -> 关羽

### 一、使用方法

##### 1、打包编译

命令：
```text
gradle :agent:shadowJar
```
或
```text
./gradlew :agent:shadowJar
```

编译后得到 agent/build/libs/agent-xxx.jar

##### 2、javaagent使用

在需要监控的应用启动时，加入以下参数用于指定当前agent程序：
```text
-javaagent:/Users/xuanyonghao/git-project/GuanYu/agent/build/libs/agent-1.0-SNAPSHOT-all.jar="denyMethodsConfigFile=/Users/threedr3am/git-project/GuanYu/conf/deny.conf"
```
denyMethodsConfigFile为agent参数，更多参数请往下翻阅！

### 二、方法调用黑名单

agent参数：denyMethodsConfigFile

例：denyMethodsConfigFile=/Users/threedr3am/git-project/GuanYu/conf/deny.conf

内容：
```text
java/lang/Runtime exec * sun/usagetracker/UsageTrackerClient
java/lang/ProcessBuilder start *
```
空格划分
- 第一项：需要拦截的类名
- 第二项：需要拦截的方法名（*表示全部拦截）
- 第三项：需要拦截的方法描述（*表示全部拦截）
- 第四项：白名单调用类（非必须）