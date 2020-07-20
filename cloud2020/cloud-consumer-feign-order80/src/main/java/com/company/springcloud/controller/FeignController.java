package com.company.springcloud.controller;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class FeignController {
    @Autowired
    private FeignService feignService;

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable(name = "id") Long id){
        return feignService.getPaymentById(id);
    }

    @GetMapping(value = "/payment/timeout")
    public String getTimeout() {
        return feignService.getTimeout();
    }
}
