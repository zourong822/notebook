package com.company.springcloud.controller;

import com.company.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/zk")
    public CommonResult getPaymentById(){
            return new CommonResult(200,"***查询成功,port: "+port,null);
    }

}
