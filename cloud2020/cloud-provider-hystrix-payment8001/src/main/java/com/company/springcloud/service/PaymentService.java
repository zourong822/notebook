package com.company.springcloud.service;

public interface PaymentService {
    String paymentInfo_OK(Integer id);

    String paymentInfo_TimeOut(Integer id);

    //熔断
    String paymentCircuitBreaker(Integer id);
}
