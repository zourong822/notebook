package com.company.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: zourong
 * @Date: 2020/4/16 10:26
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigCenterMain3377 {
    public static void main(String[] args){
        SpringApplication.run(NacosConfigCenterMain3377.class,args);
    }
}
