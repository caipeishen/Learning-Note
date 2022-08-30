package com.bilibili.juc.tl;

import lombok.Getter;
import sun.font.FontRunIterator;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class House //资源类
{
    int saleCount = 0;
    public synchronized void saleHouse()
    {
        ++saleCount;
    }

    /*ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue()
        {
            return 0;
        }
    };*/
    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);
    public void saleVolumeByThreadLocal()
    {
        saleVolume.set(1+saleVolume.get());
    }
}

/**
 * @auther zzyy
 * @create 2021-12-31 15:46
 *
 * 需求1： 5个销售卖房子，集团高层只关心销售总量的准确统计数。
 *
 * 需求2： 5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，分灶吃饭，各个销售自己动手，丰衣足食
 *
 *
 */
public class ThreadLocalDemo
{
    public static void main(String[] args) throws InterruptedException
    {

        House house = new House();

        for (int i = 1; i <=5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5)+1;
                try {
                    for (int j = 1; j <=size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t"+"号销售卖出："+house.saleVolume.get());
                } finally {
                    house.saleVolume.remove();
                }
            },String.valueOf(i)).start();
        };

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName()+"\t"+"共计卖出多少套： "+house.saleCount);
    }
}







































