package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.Test10")
public class Test10 {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        AtomicInteger r = new AtomicInteger();
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r.set(10);
        },"t1");
        t1.start();
        Thread.sleep(1500);
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
