package com.bilibili.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther zzyy
 * @create 2022-02-24 11:15
 */
public class CASDemo
{
    public static void main(String[] args)
    {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2022)+"\t"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2022)+"\t"+atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
