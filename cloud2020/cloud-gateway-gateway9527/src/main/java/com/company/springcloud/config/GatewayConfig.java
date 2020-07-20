package com.company.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zourong
 * @Date: 2020/4/13 10:59
 * @Description:
 */
@Configuration
public class GatewayConfig {
    /*
     * @Author zourong
     * @Description
     * @Date 2020/4/13 11:01
     * @Param [builder]
     * @return org.springframework.cloud.gateway.route.RouteLocator
     **/
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes=builder.routes();
        routes.route("baidu_guonei",r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        routes.route("baidu_guoji",r -> r.path("/guoji").uri("http://news.baidu.com/guoji")).build();
        return routes.build();
    }
}
