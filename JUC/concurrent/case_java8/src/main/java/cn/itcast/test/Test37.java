package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.Test37")
public class Test37 {
    public static void main(String[] args) throws ParseException, InterruptedException {
        Date a = getDate("1999-10-1");
        AtomicReference<Date> ref = new AtomicReference<>(a);

        log.debug("main start...");

        new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date b = getDate("2019-10-1");
            log.debug("change A->B {}", ref.compareAndSet(a, b));
            a.setMonth(0);
            log.debug("change B->A {}", ref.compareAndSet(b, a));
        },"t1").start();

        // 1s 后
        Thread.sleep(1000);

        // 认为 prev 仍为 A, 打印 true
        Date c = getDate("2000-10-1");
        log.debug("change A{}->C{}-{}", ref.compareAndSet(a, c), ref.get());
    }

    private static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
