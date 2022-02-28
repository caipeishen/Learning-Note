package com.itheima.pattern.iterator;

/**
 * @version v1.0
 * @ClassName: StudentIterator
 * @Description: 抽象迭代器角色接口
 * @Author: 黑马程序员
 */
public interface StudentIterator {

    //判断是否还有元素
    boolean hasNext();

    //获取下一个元素
    Student next();
}
