package cn.itcast.pattern;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

class SyncWaitNotify {
    private int flag;
    private int loopNumber;

    public SyncWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(int waitFlag, int nextFlag, String str) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

class SyncLock extends ReentrantLock {
    Condition waitSet = this.newCondition();
    private int flag;
    private int loopNumber;

    public SyncLock(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(int waitFlag, int nextFlag, String str) {
        for (int i = 0; i < loopNumber; i++) {
            this.lock();
            try {
                while (this.flag != waitFlag) {
                    try {
                        waitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                this.flag = nextFlag;
                waitSet.signalAll();
            } finally {
                this.unlock();
            }
        }
    }
}

class SyncPark {
    private int loopNumber;
    private Thread[] threads;

    public SyncPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void setThreads(Thread... threads) {
        this.threads = threads;
    }

    public void print(String str) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(nextThread());
        }
    }

    private Thread nextThread() {
        Thread current = Thread.currentThread();
        int index = 0;
        for (int i = 0; i < threads.length; i++) {
            if(threads[i] == current) {
                index = i;
                break;
            }
        }
        if(index < threads.length - 1) {
            return threads[index+1];
        } else {
            return threads[0];
        }
    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
        LockSupport.unpark(threads[0]);
    }
}



public class TestOrder {

    public static void main(String[] args) {
        test3();
    }

    private static void test3() {
        SyncPark syncPark = new SyncPark(3);
        Thread t1 = new Thread(() -> {
            syncPark.print("a");
        });
        Thread t2 = new Thread(() -> {
            syncPark.print("b");
        });
        Thread t3 = new Thread(() -> {
            syncPark.print("c\n");
        });
        syncPark.setThreads(t1, t2, t3);
        syncPark.start();
    }

    private static void test2() {
        SyncLock syncLock = new SyncLock(1, 5);
        new Thread(() -> {
            syncLock.print(1, 2, "a");
        }).start();
        new Thread(() -> {
            syncLock.print(2, 3, "b");
        }).start();
        new Thread(() -> {
            syncLock.print(3, 1, "c\n");
        }).start();
    }

    private static void test1() {
        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 5);
        new Thread(() -> {
            syncWaitNotify.print(1, 2, "a");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(2, 3, "b");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(3, 1, "c\n");
        }).start();
    }

}
