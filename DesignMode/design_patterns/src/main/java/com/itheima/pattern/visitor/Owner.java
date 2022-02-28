package com.itheima.pattern.visitor;

/**
 * @version v1.0
 * @ClassName: Owner
 * @Description: 具体访问者角色类(自己)
 * @Author: 黑马程序员
 */
public class Owner implements Person {

    public void feed(Cat cat) {
        System.out.println("主人喂食猫");
    }

    public void feed(Dog dog) {
        System.out.println("主人喂食狗");
    }
}
