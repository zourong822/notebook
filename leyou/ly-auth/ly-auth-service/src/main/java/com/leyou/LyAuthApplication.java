package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: zourong
 * @Date: 2020/7/16 08:31
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyAuthApplication {
    public static void main(String[] args){
        SpringApplication.run(LyAuthApplication.class,args);
    }
}
