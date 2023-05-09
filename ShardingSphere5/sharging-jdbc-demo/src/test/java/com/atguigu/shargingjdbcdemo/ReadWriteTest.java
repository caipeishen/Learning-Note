package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.User;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ReadWriteTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 读写分离：写入数据的测试
     */
    @Test
    public void testInsert(){

        User user = new User();
        user.setUname("张三丰");
        userMapper.insert(user);
    }

    /**
     * 读写分离：事务测试
     */
    @Transactional//开启事务
    @Test
    public void testTrans(){

        User user = new User();
        user.setUname("铁锤");
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
    }


    /**
     * 读写分离：负载均衡测试
     */
    @Test
    public void testSelectAll(){

        List<User> users1 = userMapper.selectList(null);
        List<User> users2 = userMapper.selectList(null);
        List<User> users3 = userMapper.selectList(null);
        List<User> users4 = userMapper.selectList(null);
//        users.forEach(System.out::println);
    }

}
