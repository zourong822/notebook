package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: zourong
 * @Date: 2020/4/16 09:30
 * @Description:
 */
@RestController
@RequestMapping("/consumer")
public class NacosConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;

    @GetMapping("/nacos/payment/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return restTemplate.getForObject(serviceUrl+"/nacos/payment/"+id,String.class);
    }

}
