package com.company.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/14 13:13
 * @Description:
 */
@RestController
@RefreshScope
public class ConfigController {

    @Value("${config.info}")
    private String info;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/config")
    public String getServerPort() {
        return "info:"+info+"\t\t port:"+port;
    }
}
