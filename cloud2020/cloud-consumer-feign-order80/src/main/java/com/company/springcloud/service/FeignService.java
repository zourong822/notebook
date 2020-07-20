package com.company.springcloud.service;

import com.company.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface FeignService {
    @GetMapping("/payment/get/{id}")
    @ResponseBody
    CommonResult getPaymentById(@PathVariable(name = "id") Long id);

    @GetMapping(value = "/payment/timeout")
    String getTimeout();
}
