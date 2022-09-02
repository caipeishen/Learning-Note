package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test11")
public class Test11 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000); // wait, join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("打断标记1:{}", t1.isInterrupted());
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记2:{}", t1.isInterrupted());
        log.debug("打断标记3:{}", t1.isInterrupted());
        log.debug("打断标记4:{}", t1.isInterrupted());
    }
}
