package com.yuxiao.springcloud.service.impl;

import com.yuxiao.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.UUID;

/**
 * 该类是和mq连接的类  向mq发送消息
 */

@EnableBinding(Source.class) //定义消息的推送管道 指信道channel和exchange绑定在一起 消息生产者使用Source 消费者使用Sink
public class MessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel output;//注入消息发送管道（org.springframework.messaging.MessageChannel）


    @Override
    public String sendMsg(String message) {
        String serial= UUID.randomUUID().toString();
        String msg=message+"_"+serial;
        //发送消息方法 使用MessageBuilder（org.springframework.integration.support.MessageBuilder）构建一个消息 发送  返回值是boolean类型
        boolean sendFlag = output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("发送的消息msg："+msg);
        if (sendFlag){
            return "发送成功";
        }else{
            return "发送失败";
        }
    }
}
