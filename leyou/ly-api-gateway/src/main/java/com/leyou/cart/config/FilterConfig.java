package com.leyou.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/7/16 16:53
 * @Description:
 */
@ConfigurationProperties(prefix = "ly.filter")
@Data
public class FilterConfig {

    private List<String> allowPaths;
}
