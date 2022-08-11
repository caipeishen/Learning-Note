package com.my.service;

import com.my.spring.Autowired;
import com.my.spring.BeanNameAware;
import com.my.spring.Component;
import com.my.spring.InitializingBean;

/**
 * @author caipeishen
 * @version 1.0
 * @date 2022/8/11 15:12
 */
@Component
//@Scope("prototype") //多例
public class UserService implements BeanNameAware , InitializingBean ,UserInterface{

    @Autowired
    private OrderService orderService;

    private String beanName;

    @Override
    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("初始化方法");
    }
}
