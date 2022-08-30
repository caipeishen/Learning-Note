package com.bilibili.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @auther zzyy
 * @create 2022-02-26 10:57
 */
public class AtomicMarkableReferenceDemo
{
    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100,false);

    public static void main(String[] args)
    {
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"默认标识："+marked);
            //暂停1秒钟线程,等待后面的T2线程和我拿到一样的模式flag标识，都是false
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            markableReference.compareAndSet(100,1000,marked,!marked);
        },"t1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"默认标识："+marked);

            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);
            System.out.println(Thread.currentThread().getName()+"\t"+"t2线程CASresult： "+b);
            System.out.println(Thread.currentThread().getName()+"\t"+markableReference.isMarked());
            System.out.println(Thread.currentThread().getName()+"\t"+markableReference.getReference());
        },"t2").start();
    }
}

/**
 *  CAS----Unsafe----do while+ABA---AtomicStampedReference,AtomicMarkableReference
 *
 *  AtomicStampedReference,version号，+1；
 *
 *  AtomicMarkableReference，一次，false，true
 *
 */