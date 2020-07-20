package com.company.springcloud.service;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.service.impl.OpenFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: zourong
 * @Date: 2020/4/22 14:17
 * @Description:
 */
//value表示服务名称，fallback指定兜底类，兜底类必须加入了spring容器，@Component
@FeignClient(value = "nacos-payment-provider",fallback = OpenFeignServiceImpl.class)
public interface OpenFeignService {

    @GetMapping("/payment/{id}")
    CommonResult flowLimit(@PathVariable(name = "id") String id);
}
