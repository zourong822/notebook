package com.company.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/21 16:55
 * @Description:
 */
@RestController
public class FlowLimitController {


    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "deal_byResource")
    public CommonResult byResource(){
        return new CommonResult(200,"byResource按资源名称限流调用成功。。。",new Payment(2020L,"serial001"));
    }

    public CommonResult deal_byResource(BlockException e){
        return new CommonResult(502,e.getClass().getCanonicalName(),"byResource按资源名称限流不可用。。。");
    }

    @GetMapping("/byResource/byUrl")
    @SentinelResource(value = "byUrl")  //不指定兜底的方法，则会触发默认的Blocked by Sentinel (flow limiting)
    public CommonResult byUrl(){
        return new CommonResult(200,"byResource按url限流调用成功。。。",new Payment(2020L,"serial002"));
    }

    @GetMapping("/byResource/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException")  //指定兜底类和其中的方法
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"全局兜底测试调用成功。。。",new Payment(2020L,"serial003"));
    }
}
