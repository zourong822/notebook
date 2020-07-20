package com.company.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 11:57
 * @Description:
 */
@Configuration
@MapperScan("com.company.springcloud.dao")
public class MyBatisConfig {
}
