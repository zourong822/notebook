package com.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: zourong
 * @Date: 2020/7/13 10:21
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "ly.sms")
public class SmsProperties {
    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String templateCode;
}
