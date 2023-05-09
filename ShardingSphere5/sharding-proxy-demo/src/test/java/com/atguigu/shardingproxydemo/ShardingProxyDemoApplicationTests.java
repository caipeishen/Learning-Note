package com.atguigu.shardingproxydemo;

import com.atguigu.shardingproxydemo.entity.User;
import com.atguigu.shardingproxydemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShardingProxyDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserSelectAll(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
