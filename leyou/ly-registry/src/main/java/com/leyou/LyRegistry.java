package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther: zourong
 * @Date: 2020/4/27 14:11
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class LyRegistry {
    public static void main(String[] args){
        SpringApplication.run(LyRegistry.class,args);
    }

}
