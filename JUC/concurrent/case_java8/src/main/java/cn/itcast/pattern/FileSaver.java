package cn.itcast.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j(topic = "c.FileSaver")
public class FileSaver {
    private String filename;

    private ObjectMapper mapper;
    private boolean changed;

    public FileSaver(String filename) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
    }

    public void save(ConcurrentHashMap<String, String> info) {
        log.debug("need save {}? {}", info, changed?"yes":"no");
        if(!changed) {
            return;
        }
        try {
            mapper.writeValue(new File(filename), info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        changed = false;
    }

    public void change() {
        this.changed = true;
    }
}
