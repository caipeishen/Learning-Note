package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.Test28")
public class Test28 {
    public static void main(String[] args) {
        AwaitSignal2 as = new AwaitSignal2(3);
        as.start(new Thread(() -> {
            as.print("a");
        }), new Thread(() -> {
            as.print("b");
        }), new Thread(() -> {
            as.print("c");
        }), new Thread(() -> {
            as.print("d");
        }));


    }
}

@Slf4j(topic = "c.AwaitSignal")
class AwaitSignal2 extends ReentrantLock {
    private Map<Thread, Condition[]> map = new HashMap<>();

    public void start(Thread... threads) {
        Condition[] temp = new Condition[threads.length];
        for (int i = 0; i < threads.length; i++) {
            temp[i] = this.newCondition();
        }
        for (int i = 0; i < threads.length; i++) {
            Condition current = temp[i];
            Condition next;
            if (i == threads.length - 1) {
                next = temp[0];
            } else {
                next = temp[i + 1];
            }
            map.put(threads[i], new Condition[]{current, next});
        }
        for (Thread thread : map.keySet()) {
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.lock();
        try {
            map.get(threads[0])[0].signal();
        } finally {
            this.unlock();
        }
    }

    public void print(String str) {
        for (int i = 0; i < loopNumber; i++) {
            this.lock();
            try {
                Condition[] conditions = map.get(Thread.currentThread());
                conditions[0].await();
                log.debug(str);
                conditions[1].signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.unlock();
            }
        }
    }

    // 循环次数
    private int loopNumber;

    public AwaitSignal2(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}