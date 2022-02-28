package com.itheima.pattern.factory.abstract_factory;

/**
 * @version v1.0
 * @ClassName: AmericanDessertFactory
 * @Description:
 *         美式风味的甜品工厂
 *             生产美式咖啡和抹茶慕斯
 * @Author: 黑马程序员
 */
public class AmericanDessertFactory implements DessertFactory {

    public Coffee createCoffee() {
        return new AmericanCoffee();
    }

    public Dessert createDessert() {
        return new MatchaMousse();
    }
}
