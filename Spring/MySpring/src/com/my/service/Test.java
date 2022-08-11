package com.my.service;

import com.my.spring.MyApplicationContext;

/**
 * @author caipeishen
 * @version 1.0
 * @date 2022/8/11 14:54
 */
public class Test {

    public static void main(String[] args) {

        MyApplicationContext applicationContext = new MyApplicationContext(AppConfig.class);
        UserInterface userService = (UserInterface)applicationContext.getBean("userService");
        userService.test();
    }
}
