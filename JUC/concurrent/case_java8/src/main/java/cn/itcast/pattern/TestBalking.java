package cn.itcast.pattern;

import cn.itcast.n2.util.Sleeper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestBalking {
    private static final String FILE_NAME = "d:\\mylog.log";
    private static final ConcurrentHashMap<String, String> INFO = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        FileSaver saver = new FileSaver(FILE_NAME);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            saver.save(INFO);
        }, 0L, 2L, TimeUnit.SECONDS);


        Sleeper.sleep(1);
        new Thread(()->{
            INFO.put("aaa", "bbb");
            saver.change();
        }).start();

        Sleeper.sleep(2);
        new Thread(()->{
            INFO.put("ccc", "ddd");
            saver.change();
        }).start();
    }
}
