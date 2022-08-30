package com.bilibili.juc.volatiles;

import java.util.HashMap;

/**
 * @auther zzyy
 * @create 2022-02-24 10:19
 */
public class T1
{
    volatile boolean flag;

    public static void main(String[] args)
    {
        ThreadLocal<String> tl = new ThreadLocal<>();    //line1
        tl.set("zzyybs@126.com");                       //line2
        tl.get();                                       //line3

        new HashMap<>().put(null,123);
    }
}
