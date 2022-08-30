package com.bilibili.juc.cas;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@ToString
@AllArgsConstructor
class User
{
    String userName;
    int    age;
}

/**
 * @auther zzyy
 * @create 2022-02-24 14:50
 */
public class AtomicReferenceDemo
{
    public static void main(String[] args)
    {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3",22);
        User li4 = new User("li4",28);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());


    }
}
