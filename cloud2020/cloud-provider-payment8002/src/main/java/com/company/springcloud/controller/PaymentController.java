package com.company.springcloud.controller;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if(result>0){
            return new CommonResult(200,"***插入成功,port: "+port,result);
        }else {
            return new CommonResult(400,"***插入失败");
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable(name = "id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if(null != payment){
            return new CommonResult(200,"***查询成功,port: "+port,payment);
        }else {
            return new CommonResult(400,"***查询失败，id="+id);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return port;
    }
}
