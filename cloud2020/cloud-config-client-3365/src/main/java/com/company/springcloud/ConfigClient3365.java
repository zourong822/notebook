package com.company.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Auther: zourong
 * @Date: 2020/4/13 17:25
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClient3365 {
    public static void main(String[] args){
        SpringApplication.run(ConfigClient3365.class,args);
    }
}
