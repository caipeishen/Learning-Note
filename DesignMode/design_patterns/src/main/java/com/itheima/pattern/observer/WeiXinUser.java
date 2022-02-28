package com.itheima.pattern.observer;

/**
 * @version v1.0
 * @ClassName: WeiXinUser
 * @Description: 具体的观察者角色类
 * @Author: 黑马程序员
 */
public class WeiXinUser implements Observer {

    private String name;

    public WeiXinUser(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(name + "-" + message);
    }
}
