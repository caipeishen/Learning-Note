package com.itheima.pattern.adapter.object_adapter;

/**
 * @version v1.0
 * @ClassName: TFCard
 * @Description: 适配者类的接口
 * @Author: 黑马程序员
 */
public interface TFCard {

    //从TF卡中读取数据
    String readTF();
    //往TF卡中写数据
    void writeTF(String msg);
}
