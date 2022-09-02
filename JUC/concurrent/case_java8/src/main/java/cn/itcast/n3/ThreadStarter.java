package cn.itcast.n3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.ThreadStarter")
public class ThreadStarter {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造方法的参数是给线程指定名字，推荐
        Thread t1 = new Thread("t1") {
            @Override
            // run 方法内实现了要执行的任务
            public void run() {
                log.debug("hello");
            }
        };
        t1.start();

        // 创建任务对象
        Runnable task2 = () -> log.debug("hello");

        // 参数1 是任务对象; 参数2 是线程名字，推荐
        Thread t2 = new Thread(task2, "t2");
        t2.start();


        // 创建任务对象
        FutureTask<Integer> task3 = new FutureTask<>(() -> {
            log.debug("hello");
            return 100;
        });

        // 参数1 是任务对象; 参数2 是线程名字，推荐
        new Thread(task3, "t3").start();

        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer result = task3.get();
        log.debug("结果是:{}", result);
    }
}
