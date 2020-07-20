package com.company.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: zourong
 * @Date: 2020/4/15 17:07
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderMain9002 {
    public static void main(String[] args){
        SpringApplication.run(NacosProviderMain9002.class,args);
    }
}
