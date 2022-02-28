package com.itheima.pattern.adapter.class_adapter;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * @version v1.0
 * @ClassName: SDAdapterTF
 * @Description: 适配器类
 * @Author: 黑马程序员
 */
public class SDAdapterTF extends TFCardImpl implements SDCard {

    public String readSD() {
        System.out.println("adapter read tf card");
        return readTF();
    }

    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        writeTF(msg);
    }
}
