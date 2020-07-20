package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/16 10:27
 * @Description:
 */
@RestController
@RefreshScope
@RequestMapping("/config")
public class NacosConfigCenterController {
    @Value("${config.info}")
    private String configInfo;

//    @Value("${server.port}")
//    private String port;

    @GetMapping("/info")
    public String getInfo(){
        return configInfo;
    }
}
