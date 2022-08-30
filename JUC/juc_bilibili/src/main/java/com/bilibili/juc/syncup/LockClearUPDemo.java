package com.bilibili.juc.syncup;

/**
 * @auther zzyy
 * 锁消除
 * 从JIT角度看相当于无视它，synchronized (o)不存在了,
 * 这个锁对象并没有被共用扩散到其它线程使用，
 * 极端的说就是根本没有加这个锁对象的底层机器码，消除了锁的使用
 */
public class LockClearUPDemo
{
    static Object objectLock = new Object();

    public void m1()
    {
        /*synchronized (objectLock){
            System.out.println("-----hello LockClearUPDemo");
        }*/

        //锁消除问题，JIT编译器会无视它 ，synchronized(o),每次new出来的，不存在了，非正常的。
        Object o = new Object();

        synchronized (o){
            System.out.println("-----hello LockClearUPDemo"+"\t"+o.hashCode()+"\t"+objectLock.hashCode());
        }
    }

    public static void main(String[] args)
    {
        LockClearUPDemo lockClearUPDemo = new LockClearUPDemo();

        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                lockClearUPDemo.m1();
            },String.valueOf(i)).start();
        }
    }
}
