package com.bilibili.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2022-01-18 19:37
 */
public class DeadLockDemo
{
    public static void main(String[] args)
    {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有A锁，希望获得B锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得B锁");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有B锁，希望获得A锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (objectA){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得A锁");
                }
            }
        },"B").start();
    }
}
