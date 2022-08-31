package cn.itcast.monitor.service;

import cn.itcast.monitor.controller.MonitorController;
import cn.itcast.monitor.vo.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yihang
 */
@Service
@Slf4j
public class MonitorService {

    private volatile boolean stop;
    private volatile boolean starting;
    private Thread monitorThread;

    public void start() {
        // 缩小同步范围，提升性能
        synchronized (this) {
            log.info("该监控线程已启动?({})", starting);
            if (starting) {
                return;
            }
            starting = true;
        }

        // 由于之前的 balking 模式，以下代码只可能被一个线程执行，因此无需互斥
        monitorThread = new Thread(() -> {
            while (!stop) {
                report();
                sleep(2);
            }
            // 这里的监控线程只可能启动一个，因此只需要用 volatile 保证 starting 的可见性
            log.info("监控线程已停止...");
            starting = false;
        });

        stop = false;
        log.info("监控线程已启动...");
        monitorThread.start();
    }

    private void report() {
        Info info = new Info();
        info.setTotal(Runtime.getRuntime().totalMemory());
        info.setFree(Runtime.getRuntime().freeMemory());
        info.setMax(Runtime.getRuntime().maxMemory());
        info.setTime(System.currentTimeMillis());
        MonitorController.QUEUE.offer(info);
    }

    private void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void stop() {
        stop = true;
        // 不加打断需要等到下一次 sleep 结束才能退出循环，这里是为了更快结束
        monitorThread.interrupt();
    }

}
