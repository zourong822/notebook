package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/consul")
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment")
    public String paymentConsul() {
        return "SpringCloud with consul:" + serverPort + "\t成功\t" + UUID.randomUUID().toString();
    }
}
