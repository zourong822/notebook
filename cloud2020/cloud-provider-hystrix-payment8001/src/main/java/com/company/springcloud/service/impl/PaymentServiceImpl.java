package com.company.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.company.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    /**
     * 正常访问
     *
     * @param id
     * @return
     */
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_OK,id:" + id + "\t" + "O(∩_∩)O哈哈~";
    }

    /**
     * 超时访问
     *
     * @param id
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "histrixFallback",
                    commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
                    })
    public String paymentInfo_TimeOut(Integer id) {
//        int age=10/0;
        int timeNumber = 3;
        try {
            // 暂停3秒钟
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t" +
                "O(∩_∩)O哈哈~  耗时(秒)" + timeNumber;
    }

    public String histrixFallback(Integer id){
        return "线程池:" + Thread.currentThread().getName() + " hystrixInfo_TimeOut,id:" + id + "\t" +
                "/(ㄒoㄒ)/~~";
    }


    //============服务熔断,TODO bug是由于使用错了属性defaultFallback，应该用fallbackMethod
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback",
        commandProperties = {
            //5秒未响应，则服务降级
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000"),
            //熔断相关
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            //10次请求
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            //在10秒钟内
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //失败率达到50%，则触发熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50")
    })
    @Override
    public String paymentCircuitBreaker(Integer id){
        if(id<0){
            throw new RuntimeException("***id不能为负数");
        }
        String serialNumber= IdUtil.simpleUUID();//UUID去掉下划线
        return Thread.currentThread().getName()+"\t调用成功，流水号："+serialNumber;
    }

    public String paymentCircuitBreakerFallback(Integer id){
        return "id不能为负数，请稍后再试";
    }

}
