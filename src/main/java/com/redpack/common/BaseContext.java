package com.redpack.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登陆用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程id
     * @param id
     */
    public static void setThreadLocalId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取线程id
     * @return
     */
    public static Long getThreadLocalId(){
        return threadLocal.get();
    }
}
