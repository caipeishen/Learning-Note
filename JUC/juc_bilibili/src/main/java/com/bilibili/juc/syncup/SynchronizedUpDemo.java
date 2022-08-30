package com.bilibili.juc.syncup;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 */
public class SynchronizedUpDemo
{
    public static void main(String[] args)
    {
        /*//先睡眠5秒，保证开启偏向锁
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        Object o = new Object();
        System.out.println("本应是偏向锁");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        o.hashCode();//没有重写，一致性哈希，重写后无效,当一个对象已经计算过identity hash code，它就无法进入偏向锁状态；

        synchronized (o){
            System.out.println("本应是偏向锁，但是由于计算过一致性哈希，会直接升级为轻量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }*/

        //先睡眠5秒，保证开启偏向锁
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        Object o = new Object();

        synchronized (o){
            o.hashCode();//没有重写，一致性哈希，重写后无效
            System.out.println("偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向模式，膨胀为重量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }




    private static void thinLock()
    {
        Object o = new Object();

        new Thread(() -> {
            synchronized (o){
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();
    }

    private static void biasedLock()
    {
    /* //暂停几秒钟线程
     try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
     Object o = new Object();
     System.out.println(ClassLayout.parseInstance(o).toPrintable());

     System.out.println("=======================");
     new Thread(() -> {
         synchronized (o){
             System.out.println(ClassLayout.parseInstance(o).toPrintable());
         }
     },"t1").start();*/
        //先睡眠5秒，保证开启偏向锁
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("=========================================");
        new Thread(() -> {
            synchronized (o){
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();
    }

    private static void noLock()
    {
        Object o = new Object();

        System.out.println("10进制："+o.hashCode());
        System.out.println("16进制："+Integer.toHexString(o.hashCode()));
        System.out.println("2进制："+Integer.toBinaryString(o.hashCode()));

        //2进制：1001010010101110100011110010101
        //      1001010010101110100011110010101
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}