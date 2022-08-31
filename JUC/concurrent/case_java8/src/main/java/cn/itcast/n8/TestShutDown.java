package cn.itcast.n8;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestShutDown")
public class TestShutDown {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<Integer> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            Thread.sleep(1000);
            log.debug("task 1 finish...");
            return 1;
        });

        Future<Integer> result2 = pool.submit(() -> {
            log.debug("task 2 running...");
            Thread.sleep(1000);
            log.debug("task 2 finish...");
            return 2;
        });

        Future<Integer> result3 = pool.submit(() -> {
            log.debug("task 3 running...");
            Thread.sleep(1000);
            log.debug("task 3 finish...");
            return 3;
        });

        log.debug("shutdown");
//        pool.shutdown();
//        pool.awaitTermination(3, TimeUnit.SECONDS);
        List<Runnable> runnables = pool.shutdownNow();
        log.debug("other.... {}" , runnables);
    }
}
