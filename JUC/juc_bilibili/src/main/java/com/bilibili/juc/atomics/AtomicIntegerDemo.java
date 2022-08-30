package com.bilibili.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber
{
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus()
    {
        atomicInteger.getAndIncrement();
    }
}


/**
 * @auther zzyy
 * @create 2022-02-25 21:59
 */
public class AtomicIntegerDemo
{
    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException
    {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 1; i <=SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <=1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }
        //等待上面50个线程全部计算完成后，再去获得最终值

        //暂停几秒钟线程
        //try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t"+"result: "+myNumber.atomicInteger.get());
    }
}
