package com.bilibili.juc.cas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

@NoArgsConstructor
@AllArgsConstructor
@Data
class Book
{
    private int id;
    private String bookName;
}

/**
 * @auther zzyy
 * @create 2022-02-24 17:34
 */
public class AtomicStampedDemo
{
    public static void main(String[] args)
    {
        Book javaBook = new Book(1,"javaBook");

        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(javaBook,1);

        System.out.println(stampedReference.getReference()+"\t"+stampedReference.getStamp());

        Book mysqlBook = new Book(2,"mysqlBook");

        boolean b;
        b = stampedReference.compareAndSet(javaBook, mysqlBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);

        System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());


        b = stampedReference.compareAndSet(mysqlBook, javaBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);

        System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());

    }
}
