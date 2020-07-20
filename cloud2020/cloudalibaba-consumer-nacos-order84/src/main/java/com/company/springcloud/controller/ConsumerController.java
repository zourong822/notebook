package com.company.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: zourong
 * @Date: 2020/4/22 11:55
 * @Description:
 */
@RestController
public class ConsumerController {
    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/consumer/fallback/{id}")
    //只配置fallback，只管业务代码异常
//    @SentinelResource(value="consumer" ,fallback = "consumerFallback")
    //只配置blockHandler，只管sentinel配置违规情况，url有包含PathVariable的只能配置资源名，配置url会触发默认兜底
//    @SentinelResource(value="consumer" ,blockHandler = "consumerBlockHandler")
    //只配置blockHandler，只管sentinel配置违规情况，url有包含PathVariable的只能配置资源名，配置url会触发默认兜底
    @SentinelResource(value="consumer" ,
            blockHandler = "consumerBlockHandler",
            fallback = "consumerFallback",
            exceptionsToIgnore = {NullPointerException.class})//exceptionsToIgnore属性表示忽略该异常，不进入兜底方法，报ErrorPage
    public CommonResult getFallback(@PathVariable(name="id")Integer id){
        if(4==id){
            throw new IllegalArgumentException("非法参数异常。。。");
        }else if(id>4){
            throw new NullPointerException("空指针异常。。");
        }
        return restTemplate.getForObject(serviceUrl+"/payment/"+id,CommonResult.class);
    }

    /**
     * @Author zourong
     * @Description sentinel兜底方法,参数必须加上Throwable，Exception不好使。
     * @Date 2020/4/22 13:26
     * @Param [id, e]
     * @return com.company.springcloud.entities.CommonResult
     **/
    public CommonResult consumerFallback(Integer id,Throwable e){
        return new CommonResult(502,"fallback兜底方法启用，服务器繁忙="+e.getMessage());
    }

    public CommonResult consumerBlockHandler(Integer id,BlockException e){
        return new CommonResult(502,"blockHandler兜底方法启用，服务器繁忙="+e.getMessage());
    }
    //=======================openfeign
    @Autowired
    private OpenFeignService openFeignService;
    @GetMapping("/consumer/payment/{id}")
    CommonResult flowLimit(@PathVariable(name = "id") String id){
      return openFeignService.flowLimit(id);
    }
}
