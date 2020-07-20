package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/15 17:08
 * @Description:
 */
@RestController
@RequestMapping("/nacos")
public class NacosProviderController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/payment/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "nacos registry,serverPort:"+port+"\t,id:"+id;
    }

}
