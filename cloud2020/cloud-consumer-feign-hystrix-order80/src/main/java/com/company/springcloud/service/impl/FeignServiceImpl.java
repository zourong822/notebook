package com.company.springcloud.service.impl;

import com.company.springcloud.service.FeignService;
import org.springframework.stereotype.Component;

/**
 * 处理feign调用失败降级
 */
@Component
public class FeignServiceImpl implements FeignService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "===public String paymentInfo_OK(Integer id)";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "===public String paymentInfo_TimeOut(Integer id)";
    }
}
