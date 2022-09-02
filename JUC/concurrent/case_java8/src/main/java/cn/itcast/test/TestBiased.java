//package cn.itcast.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.openjdk.jol.info.ClassLayout;
//
//import java.util.Vector;
//import java.util.concurrent.locks.LockSupport;
//
//@Slf4j(topic = "c.TestBiased")
//public class TestBiased {
//
//    static Thread t1,t2,t3;
//
//    public static void main(String[] args) throws InterruptedException {
//        test4();
//    }
//    private static void test4() throws InterruptedException {
//        Vector<Dog> list = new Vector<>();
//
//        int loopNumber = 38;
//        t1 = new Thread(() -> {
//            for (int i = 0; i < loopNumber; i++) {
//                Dog d = new Dog();
//                list.add(d);
//                synchronized (d) {
//                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//                }
//            }
//            LockSupport.unpark(t2);
//        }, "t1");
//        t1.start();
//
//        t2 = new Thread(() -> {
//            LockSupport.park();
//            log.debug("===============> ");
//            for (int i = 0; i < loopNumber; i++) {
//                Dog d = list.get(i);
//                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//                synchronized (d) {
//                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//                }
//                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//            }
//            LockSupport.unpark(t3);
//        }, "t2");
//        t2.start();
//
//        t3 = new Thread(() -> {
//            LockSupport.park();
//            log.debug("===============> ");
//            for (int i = 0; i < loopNumber; i++) {
//                Dog d = list.get(i);
//                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//                synchronized (d) {
//                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//                }
//                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintableSimple(true));
//            }
//        }, "t3");
//        t3.start();
//
//        t3.join();
//        log.debug(ClassLayout.parseInstance(new Dog()).toPrintableSimple(true));
//    }
//}
//
//class Dog {
//
//}
