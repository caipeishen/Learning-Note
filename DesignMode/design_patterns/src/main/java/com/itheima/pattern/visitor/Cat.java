package com.itheima.pattern.visitor;

/**
 * @version v1.0
 * @ClassName: Cat
 * @Description: 具体元素角色类（宠物猫）
 * @Author: 黑马程序员
 */
public class Cat implements Animal {

    public void accept(Person person) {
        person.feed(this); //访问者给宠物猫喂食
        System.out.println("好好吃，喵喵喵。。。");
    }
}
