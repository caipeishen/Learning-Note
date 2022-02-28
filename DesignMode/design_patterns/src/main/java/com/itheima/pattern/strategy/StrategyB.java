package com.itheima.pattern.strategy;

/**
 * @version v1.0
 * @ClassName: StrategyB
 * @Description: 具体策略类，封装算法
 * @Author: 黑马程序员
 */
public class StrategyB implements Strategy {

    public void show() {
        System.out.println("满200元减50元");
    }
}
