package cn.itcast.pattern;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.pattern.Downloader.download;

@Slf4j(topic = "c.TestGuardedObjectV3")
public class TestGuardedObjectV3 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            GuardedObjectV3 v3 = Fetures.createFeture();

            new Thread(() -> {
                log.debug("waiting id({})...", v3.getId());
                log.debug("get response id({}): [{}] lines", v3.getId(), ((List<String>) v3.get()).size());
            }).start();

            new Thread(() -> {
                try {
                    List<String> lines = download();
                    log.debug("download complete id({})...", v3.getId());
                    Fetures.complete(v3.getId(), lines);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
}

class Fetures {
    private static final ConcurrentHashMap<Integer, GuardedObjectV3> FETURES = new ConcurrentHashMap<>();
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    public static GuardedObjectV3 createFeture() {
        // 为每个 GuardedObject 分配一个 id
        int id = ID_GENERATOR.incrementAndGet();
        GuardedObjectV3 v3 = new GuardedObjectV3(id);
        // 放入公共位置，将来异步响应返回时，根据编号获取
        FETURES.put(id, v3);
        return v3;
    }

    public static void complete(int id, Object response) {
        // 异步响应完成，根据编号获取并移除
        GuardedObjectV3 v3 = FETURES.remove(id);
        if (v3 != null) {
            v3.complete(response);
        }
    }
}


/**
 * 添加多任务处理
 */
class GuardedObjectV3 {

    private int id;
    private Object response;
    private final Object lock = new Object();


    public GuardedObjectV3(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object get() {
        synchronized (lock) {
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (lock) {
            this.response = response;
            lock.notifyAll();
        }
    }
}