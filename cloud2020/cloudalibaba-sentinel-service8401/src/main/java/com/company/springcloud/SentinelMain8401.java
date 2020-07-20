package com.company.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: zourong
 * @Date: 2020/4/20 16:53
 * @Description: Flow Limit流控测试
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelMain8401 {
    public static void main(String[] args){
        SpringApplication.run(SentinelMain8401.class,args);
    }
}
