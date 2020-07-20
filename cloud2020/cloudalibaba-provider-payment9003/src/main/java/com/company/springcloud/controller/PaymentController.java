package com.company.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.company.springcloud.entities.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: zourong
 * @Date: 2020/4/22 11:31
 * @Description:
 */
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;

    private static HashMap<String,String> map=new HashMap<>();
    static {
        map.put("1", IdUtil.simpleUUID());
        map.put("2", IdUtil.simpleUUID());
        map.put("3", IdUtil.simpleUUID());
    }
    @GetMapping("/payment/{id}")
    public CommonResult flowLimit(@PathVariable(name = "id") String id){
        String temp = map.get(id);
        if(null ==temp){
            throw new NullPointerException("空指针异常。。");
        }
        return new CommonResult(200,port+"调用成功",id+"-"+temp);
    }
}
