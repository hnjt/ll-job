package com.config.mq;

/**
 * MQ坐标类
 * by ChenYb date 2019/7/22
 */
public class MQCoordinate {

    /**
     * 坐标
     */
    public static final String EXCHANGE_A = "sync_A"; //交换机
    public static final String ROUTINGKEY_A = "sync_routingKey_A";//路由
    public static final String QUEUE_A = "sync_queue_A";//队列

    public static final String EXCHANGE_B = "sync_B"; //交换机
    public static final String ROUTINGKEY_B = "sync_routingKey_B";//路由
    public static final String QUEUE_B= "sync_queue_B";//队列
}
