package com.bilibili.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @auther zzyy
 * @create 2022-02-26 18:51
 */
public class LongAdderAPIDemo
{
    public static void main(String[] args)
    {
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator()
        {
            @Override
            public long applyAsLong(long left, long right)
            {
                return left + right;
            }
        },0);

        longAccumulator.accumulate(1);//1
        longAccumulator.accumulate(3);//4

        System.out.println(longAccumulator.get());
    }
}
