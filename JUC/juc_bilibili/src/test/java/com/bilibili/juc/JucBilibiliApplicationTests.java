package com.bilibili.juc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JucBilibiliApplicationTests
{

    @Test
    void contextLoads()
    {
        int i = 0;
        System.out.println(i++);
    }

}
