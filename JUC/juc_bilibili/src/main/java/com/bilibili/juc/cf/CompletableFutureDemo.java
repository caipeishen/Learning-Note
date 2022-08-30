package com.bilibili.juc.cf;

import java.util.concurrent.*;

/**
 * @auther zzyy
 * @create 2022-01-14 11:02
 */
public class CompletableFutureDemo
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask,"t1");
        t1.start();

        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<String>
{
    @Override
    public String call() throws Exception
    {
        System.out.println("-----come in call() " );
        return "hello Callable";
    }
}
