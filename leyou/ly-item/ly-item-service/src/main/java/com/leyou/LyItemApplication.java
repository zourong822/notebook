package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: zourong
 * @Date: 2020/6/4 17:35
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.mapper")
public class LyItemApplication {
    public static void main(String[] args){
        SpringApplication.run(LyItemApplication.class,args);
    }
}
