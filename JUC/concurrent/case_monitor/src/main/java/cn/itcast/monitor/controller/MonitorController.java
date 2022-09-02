package cn.itcast.monitor.controller;

import cn.itcast.monitor.service.MonitorService;
import cn.itcast.monitor.vo.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yihang
 */
@RestController
public class MonitorController {
    public static ArrayBlockingQueue<Info> QUEUE = new ArrayBlockingQueue(30);

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/info")
    public List<Info> info() {
        ArrayList<Info> infos = new ArrayList<>();
        QUEUE.drainTo(infos);
        return infos;
    }

    @GetMapping("/start")
    public void start() {
        monitorService.start();
    }

    @GetMapping("/stop")
    public void stop() {
        monitorService.stop();
    }
}
