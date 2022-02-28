package com.itheima.pattern.decorator;

/**
 * @version v1.0
 * @ClassName: FriedRice
 * @Description: 炒饭(具体构件角色)
 * @Author: 黑马程序员
 */
public class FriedRice extends FastFood {

    public FriedRice() {
        super(10,"炒饭");
    }

    public float cost() {
        return getPrice();
    }
}
