package cn.itcast.n4;

import java.util.concurrent.TimeUnit;

public class TestThreadStatus {
    static final Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            synchronized (obj) {
                try {
                    obj.wait();
                    Thread thread = Thread.currentThread();
                    System.out.println(thread.getName() + " " + thread.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(()->{
            synchronized (obj) {
                try {
                    obj.wait();
                    Thread thread = Thread.currentThread();
                    System.out.println(thread.getName() + " " + thread.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        TimeUnit.SECONDS.sleep(1);
        synchronized (obj) {
            System.out.println("notify begin ..."); // 断点
            obj.notifyAll();
        }
        System.out.println("notify end ..."); // 断点
    }
}
