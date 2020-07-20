package com.company.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: zourong
 * @Date: 2020/4/20 16:54
 * @Description:
 */
@RestController
@Slf4j
public class SentinelDemoController {

    @GetMapping("/testA")
    public String testA(){
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return Thread.currentThread().getName()+"==============testA";
    }
    @GetMapping("/testB")
    public String testB(){
        return "==============testB";
    }

    @GetMapping("/testC")
    public String testC(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testC RT平均时间");
        return "==============testC";
    }

    @GetMapping("/testD")
    public String testD(){
        log.info("testD 异常比例");
        int temp=10/0;
        return "==============testD";
    }

    @GetMapping("/testE")
    public String testE(){
        log.info("testE 分钟级异常数");//配置时需注意时间窗时长需大于60秒
        int temp=10/0;
        return "==============testE";
    }

    @GetMapping("/testHotKey")
    //SentinelResource中的value表示资源名，需唯一，建议与路径一致。blockHandler只负责违背限流规则的情况，代码抛异常不管。
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value="p1",required = false)String p1,
                             @RequestParam(value="p2",required = false)String p2){
        log.info("testHotKey 热点参数限流");
//        int temp=10/0;
        return "==============testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException e){
        log.info("热点参数限流：p1:{},p2:{},\nexception:{}",p1,p2,e.getMessage());
        return "deal_testHotKey o(╥﹏╥)o";
    }
}
