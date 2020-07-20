package com.company.springcloud.service.impl;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.service.OpenFeignService;
import org.springframework.stereotype.Component;

/**
 * @Auther: zourong
 * @Date: 2020/4/22 14:24
 * @Description: openfeign自动降级处理类
 */
@Component
public class OpenFeignServiceImpl implements OpenFeignService {
    @Override
    public CommonResult flowLimit(String id) {
        return new CommonResult(502,"openfeign兜底方法启用，服务器繁忙=");
    }
}
