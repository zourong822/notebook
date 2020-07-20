package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: zourong
 * @Date: 2020/6/12 18:07
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadService {
    public static void main(String[] args){
        SpringApplication.run(LyUploadService.class,args);
    }
}
