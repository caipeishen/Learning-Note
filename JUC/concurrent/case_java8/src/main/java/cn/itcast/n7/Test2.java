package cn.itcast.n7;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int sum = sum((a, b) -> a + b, 100, -10, 1);
            }).start();
        }
    }

    public static int sum(BiFunction<Integer, Integer, Integer> function, int init, int amount, int n) {
        if (n == 1) {
            return (function.apply(init, amount));
        }
        int s = sum(function, (function.apply(init, amount)), amount, n - 1);
        return s;
    }
}

class Accumulator implements BiFunction<Integer, Integer, Integer> {
    @Override
    public Integer apply(Integer a, Integer b) {
        return new Integer(a + b);
    }
}
