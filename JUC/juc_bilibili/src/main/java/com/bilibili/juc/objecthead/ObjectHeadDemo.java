package com.bilibili.juc.objecthead;


import org.openjdk.jol.info.ClassLayout;

/**
 * @auther zzyy
 * @create 2022-03-05 17:16
 */
public class ObjectHeadDemo
{
    public static void main(String[] args)
    {
        Object o = new Object();//? new一个对象，占内存多少？

        System.out.println(o.hashCode());//这个hashcode记录在对象的什么地方？

        synchronized (o){

        }

        System.gc();//手动收集垃圾。。。。。,15次可以从新生代---养老区

        Customer c1 = new Customer();
        Customer c2 = new Customer();
        Customer c3 = new Customer();
    }
}
