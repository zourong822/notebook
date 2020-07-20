package com.company.springcloud.controller;

import com.company.springcloud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/14 17:27
 * @Description:
 */
@RestController
public class SendMessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/sendMessage")
    public String sendMessage() {
        String send = messageService.send();
        return send;
    }
}
