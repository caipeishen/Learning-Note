package com.bilibili.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2022-01-22 12:45
 */
public class VolatileSeeDemo
{
    //static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args)
    {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t -----come in");
            while(flag)
            {

            }
            System.out.println(Thread.currentThread().getName()+"\t -----flag被设置为false，程序停止");
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        flag = false;

        System.out.println(Thread.currentThread().getName()+"\t 修改完成flag: "+flag);


    }
}
