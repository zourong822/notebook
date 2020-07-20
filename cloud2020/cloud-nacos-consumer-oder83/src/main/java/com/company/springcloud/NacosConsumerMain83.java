package com.company.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: zourong
 * @Date: 2020/4/16 09:21
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerMain83 {
    public static void main(String[] args){
        SpringApplication.run(NacosConsumerMain83.class,args);
    }
}
