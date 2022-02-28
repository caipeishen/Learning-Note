package com.itheima.pattern.decorator;

/**
 * @version v1.0
 * @ClassName: FriedNoodles
 * @Description: 炒面(具体的构件角色)
 * @Author: 黑马程序员
 */
public class FriedNoodles extends FastFood {

    public FriedNoodles() {
        super(12,"炒面");
    }

    public float cost() {
        return getPrice();
    }
}
