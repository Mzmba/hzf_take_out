package com.ka.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 837312868@qq.com
 * @version : 1.0
 * @Project : reggie
 * @Package : com.ka.utils
 * @createTime : 2022/9/15 19:58 星期四
 * @Description :
 */
/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
