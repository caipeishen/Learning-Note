package com.bilibili.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @auther zzyy
 * @create 2022-01-17 15:20
 */
public class CompletableFutureAPIDemo
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {

    }

    /**
     * 获得结果和触发计算
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void group1() throws InterruptedException, ExecutionException
    {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        //System.out.println(completableFuture.get());
        //System.out.println(completableFuture.get(2L,TimeUnit.SECONDS));
        //System.out.println(completableFuture.join());

        //暂停几秒钟线程
        //try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        //System.out.println(completableFuture.getNow("xxx"));
        System.out.println(completableFuture.complete("completeValue")+"\t"+completableFuture.get());
    }
}
