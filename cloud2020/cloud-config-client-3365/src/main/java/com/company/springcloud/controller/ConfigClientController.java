package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/13 17:27
 * @Description:
 */
@RestController
@RefreshScope   //启用配置自动刷新功能
public class ConfigClientController {
    @Value("${config.info}")
    private String config;
    @GetMapping("/config")
    public String config(){
        return config;
    }
}
