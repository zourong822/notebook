package com.company.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.company.springcloud.service.MessageService;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Auther: zourong
 * @Date: 2020/4/14 17:19
 * @Description:
 */
@EnableBinding(Source.class)//定义消息推送通道
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageChannel output;//消息发送通道
    @Override
    public String send() {
        String serial = IdUtil.simpleUUID();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("serial is:\t"+serial);
        return serial;
    }
}
