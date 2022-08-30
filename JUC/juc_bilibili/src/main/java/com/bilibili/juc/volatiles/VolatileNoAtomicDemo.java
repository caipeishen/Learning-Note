package com.bilibili.juc.volatiles;

import java.util.concurrent.TimeUnit;

class MyNumber
{
    volatile int number;

    public void addPlusPlus()
    {
        number++;
    }
}

/**
 * @auther zzyy
 * @create 2022-02-23 16:54
 */
public class VolatileNoAtomicDemo
{
    public static void main(String[] args)
    {
        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                for (int j = 1; j <=1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(myNumber.number);

    }
}
