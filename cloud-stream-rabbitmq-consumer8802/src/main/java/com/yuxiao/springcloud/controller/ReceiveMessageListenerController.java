package com.yuxiao.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 从mq接收消息的类
 */
@Component
@EnableBinding(Sink.class) //定义消息的推送管道 指信道channel和exchange绑定在一起 消息生产者使用Source 消费者使用Sink
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    //该注解用户消费者对消息队列消息的接收
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("消费者1     ===========》 收到的消息："+message.getPayload()+"\t"+" server port:"+serverPort);

    }


}
