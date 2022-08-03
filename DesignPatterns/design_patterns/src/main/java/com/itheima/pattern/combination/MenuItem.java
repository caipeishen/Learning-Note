package com.itheima.pattern.combination;

import com.itheima.principles.demo1.SougouInput;

/**
 * @version v1.0
 * @ClassName: MenuItem
 * @Description: 菜单项类 ： 属于叶子节点
 * @Author: 黑马程序员
 */
public class MenuItem extends MenuComponent {

    public MenuItem(String name,int level) {
        this.name = name;
        this.level = level;
    }

    public void print() {
        //打印菜单项的名称
        for(int i = 0; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(name);
    }
}
