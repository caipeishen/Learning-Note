package com.bilibili.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2022-01-19 21:35
 */
public class InterruptDemo2
{
    public static void main(String[] args)
    {
        //实例方法interrupt()仅仅是设置线程的中断状态位设置为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <=300; i++)
            {
                System.out.println("-----: "+i);
            }
            System.out.println("t1线程调用interrupt()后的的中断标识02："+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识："+t1.isInterrupted());//false

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        t1.interrupt();//true
        System.out.println("t1线程调用interrupt()后的的中断标识01："+t1.isInterrupted());//true

        try { TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("t1线程调用interrupt()后的的中断标识03："+t1.isInterrupted());//????---false中断不活动的线程不会产生任何影响。
    }
}
