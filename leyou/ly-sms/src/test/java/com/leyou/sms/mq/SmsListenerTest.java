package com.leyou.sms.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsListenerTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendVerifySms() throws InterruptedException {
        Map<String,String > paramMap =new HashMap<>();
        paramMap.put("phone","16620168227");
        paramMap.put("code","666666");
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",paramMap);
        TimeUnit.SECONDS.sleep(10);
    }
}