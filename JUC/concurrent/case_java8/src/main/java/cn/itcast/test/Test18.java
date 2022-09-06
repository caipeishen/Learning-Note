package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test18")
public class Test18 {
    static final Object lock = new Object();
    public static void main(String[] args) {


        new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notifyAll();
            }
        }, "t1").start();

            synchronized (lock) {
                try {
                    System.out.println("---1");
                    lock.wait();
                    System.out.println("---2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


    }
}
