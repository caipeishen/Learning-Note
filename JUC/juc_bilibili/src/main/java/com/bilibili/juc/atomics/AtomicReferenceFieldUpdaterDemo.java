package com.bilibili.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar //资源类
{
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar,Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");

    public void init(MyVar myVar)
    {
        if (referenceFieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE))
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"----- start init,need 2 seconds");
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"----- over init");
        }else{
            System.out.println(Thread.currentThread().getName()+"\t"+"----- 已经有线程在进行初始化工作。。。。。");
        }
    }
}


/**
 * @auther zzyy
 * 需求：
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，
 * 要求只能被初始化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdaterDemo
{
    public static void main(String[] args)
    {
        MyVar myVar = new MyVar();

        for (int i = 1; i <=5; i++) {
            new Thread(() -> {
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
    }
}
