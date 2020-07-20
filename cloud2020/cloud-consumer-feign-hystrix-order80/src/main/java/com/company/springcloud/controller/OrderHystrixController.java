package com.company.springcloud.controller;

import com.company.springcloud.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/consumer")
//配置全局的降级方法
//@DefaultProperties(defaultFallback = "global_default_fallback")
public class OrderHystrixController {
    @Autowired
    private FeignService feignService;
    /**
     * 正常访问
     *
     * @param id
     * @return
     */
    @GetMapping("/hystrix/ok/{id}")
//    @HystrixCommand
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
//        int age=10/0;
        String result = feignService.paymentInfo_OK(id);
        return result;
    }

    /**
     * 超时访问
     *
     * @param id
     * @return
     */
    @GetMapping("/hystrix/timeout/{id}")
    //配置当前方法的降级方法
//    @HystrixCommand(fallbackMethod = "counsumerFallback",
//                    commandProperties = {
//                            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//                    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        int age=10/0;
        String result = feignService.paymentInfo_TimeOut(id);
        return result;
    }

    //单个降级方法
    public String counsumerFallback(Integer id){
        return "消费方80返回，支付方繁忙或者80端报错";
    }

    //当前类全局的降级方法
    public String global_default_fallback(){
        return "80端Global降级方法。";
    }
}
