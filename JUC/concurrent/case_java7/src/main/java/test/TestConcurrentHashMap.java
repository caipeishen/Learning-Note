package test;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    public static void main(String[] args) {
        new ConcurrentHashMap(16, 0.75f, 16);
    }
}
