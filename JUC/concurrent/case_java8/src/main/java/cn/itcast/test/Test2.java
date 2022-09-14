package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        String ss1 = new String("abc");
        ss1.intern();
        String ss = "abc";
        System.out.println(ss==ss1);// false

        int b = 49;
        Integer c = Integer.valueOf(49);
        System.out.println( b ==c );
    }
}
