package com.itheima.pattern.proxy.static_proxy;

/**
 * @version v1.0
 * @ClassName: ProxyPoint
 * @Description: 代售点类
 * @Author: 黑马程序员
 */
public class ProxyPoint implements SellTickets {

    //声明火车站类对象
    private TrainStation trainStation  = new TrainStation();

    public void sell() {
        System.out.println("代售点收取一些服务费用");
        trainStation.sell();
    }

}
