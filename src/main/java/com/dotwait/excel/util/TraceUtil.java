package com.dotwait.excel.util;

/**
 * 日志跟踪工具类
 *
 * @author DotWait
 * @Date 2019-11-10
 */
public class TraceUtil {
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long stopTime;

    /**
     * 开始计时
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * 暂停打印日志
     *
     * @param message 日志消息
     */
    public void stop(String message) {
        stopTime = System.currentTimeMillis();
        System.out.println(message + ", 耗时：" + (stopTime - startTime) + "ms");
    }
}
