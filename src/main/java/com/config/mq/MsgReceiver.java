//package com.config.mq;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * 这里只做为监听者demo
// */
//@Component
//@RabbitListener(queues = MQCoordinate.QUEUE_A)
//public class MsgReceiver {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @RabbitHandler
//    public void process(String content) {
//        logger.info("处理器one接收处理队列A当中的消息： " + content);
//    }
//}
