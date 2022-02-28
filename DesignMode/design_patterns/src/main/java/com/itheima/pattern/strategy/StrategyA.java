package com.itheima.pattern.strategy;

/**
 * @version v1.0
 * @ClassName: StrategyA
 * @Description: 具体策略类，封装算法
 * @Author: 黑马程序员
 */
public class StrategyA implements Strategy {

    public void show() {
        System.out.println("买一送一");
    }
}
