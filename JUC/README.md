## JUC

> 推荐尚硅谷-周阳： https://www.bilibili.com/video/BV1ar4y1x727





> 资料：

+ 并发编程.pdf
+ 并发编程_模式.pdf
+ 并发编程_应用.pdf
+ 并发编程_原理.pdf
+ 尚硅谷-JUC.md



> 黑马和尚硅谷都不错，黑马更全，很多小知识点会涉及到，有助于理解

+ 静下心看黑马的视频配套资料最好
+ 字节码可以不用怎么看，了解就行，这些涉及到JVM



### 为什么多线程不安全？

> 原因就是：1.cpu以时间片形式执行线程，会发生**上下文切换**  2.多个线程**共享写**了同一个资源

+ 老王（操作系统）有一个功能强大的算盘（CPU），现在想把它租出去，赚一点外快 

+ 小南、小女（线程）来使用这个算盘来进行一些计算，并按照时间给老王支付费用

+ 但小南不能一天24小时使用算盘，他经常要小憩一会（sleep），又或是去吃饭上厕所（阻塞 io 操作），有 

  时还需要一根烟，没烟时思路全无（wait）这些情况统称为（阻塞） 

+ 在这些时候，算盘没利用起来（不能收钱了），老王觉得有点不划算 

+ 另外，小女也想用用算盘，如果总是小南占着算盘，让小女觉得不公平

+ 于是，老王灵机一动，想了个办法 [ 让他们每人用一会，轮流使用算盘 ] 

+ 这样，当小南阻塞的时候，算盘可以分给小女使用，不会浪费，反之亦然 

+ 最近执行的计算比较复杂，需要存储一些中间结果，而学生们的脑容量（工作内存）不够，所以老王申请了 

  一个笔记本（主存），把一些中间结果先记在本上

+ 计算流程是这样的 

  ![](./images/多线程为什么不安全.png)

+ 但是由于分时系统，有一天还是发生了事故

+ 小南刚读取了初始值 0 做了个 +1 运算，还没来得及写回结果

+ 老王说 [ 小南，你的时间到了，该别人了，记住结果走吧 ]，于是小南念叨着 [ 结果是1，结果是1...] 不甘心地 到一边待着去了（上下文切换）

+ 老王说 [ 小女，该你了 ]，小女看到了笔记本上还写着 0 做了一个 -1 运算，将结果 -1 写入笔记本

+ 这时小女的时间也用完了，老王又叫醒了小南：[小南，把你上次的题目算完吧]，小南将他脑海中的结果 1 写入了笔记本

+ ![](./images/多线程为什么不安全2.png)

+ 小南和小女都觉得自己没做错，但笔记本里的结果是 1 而不是 0



### Monitor原理

加锁的this，指针指向monitor对象（也成为冠城或监视器锁）的起始地址。每个对象都存在一个monitor与之关联，当一个monitor被某个线程持有后，它便处于锁定状态。在Java虚拟机中(HotSpot)中，monitor是由ObjectMonitor实现的，其主要数据结构如下(ObjectMpnitor.hpp文件，C++实现的)



> Monitor流程-加锁

![](C:/Users/peish/Desktop/Learning-Note/JUC/images/monitor流程-加锁.png)



> Monitor流程-解锁

![](./images/monitor流程-解锁.png)



### 六种状态

参考：./并发编程.pdf/3.13六种状态 

```java
@Slf4j(topic = "c.TestState")
public class TestState {
    public static void main(String[] args) throws IOException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            } // new
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                while(true) { // runnable

                }
            }
        };
        t2.start();

        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                log.debug("running...");
            } // TERMINATED (下面代码睡眠了500ms)
        };
        t3.start();

        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                synchronized (TestState.class) {
                    try {
                        Thread.sleep(1000000); // timed_waiting
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();

        Thread t5 = new Thread("t5") {
            @Override
            public void run() { // waiting
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();

        Thread t6 = new Thread("t6") {
            @Override
            public void run() {
                synchronized (TestState.class) { // blocked
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
        System.in.read();
    }
}
```

