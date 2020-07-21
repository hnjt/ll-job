package com.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitMQConfig {
 
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        //rabbitMQ 连接
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope("prototype")//必须是prototype类型,处理rabbitTemplate 对象唯一性
    public RabbitTemplate rabbitTemplate() {
        //生成模板对象
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }


    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(MQCoordinate.EXCHANGE_A);
//    }

    /**
     * 获取队列A
     * @return
     */
//    @Bean
//    public Queue queueA() {
//        return new Queue(MQCoordinate.QUEUE_A, true); //队列持久
//    }

    /**
     * 一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
     * 设置多个 , 添加多个binding方法即可
     * @return
     */
//    @Bean
//    public Binding binding() {
//
//        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(MQCoordinate.ROUTINGKEY_A);
//    }

}
