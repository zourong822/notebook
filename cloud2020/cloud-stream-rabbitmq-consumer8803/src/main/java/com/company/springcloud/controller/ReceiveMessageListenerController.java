package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Auther: zourong
 * @Date: 2020/4/15 9:36
 * @Description:
 */
@Component
@EnableBinding(Sink.class)//表示消息消费端
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String port;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("我是消费者2号，-----》接受到的消息是："+message.getPayload()+"\t"+port);
    }
}
