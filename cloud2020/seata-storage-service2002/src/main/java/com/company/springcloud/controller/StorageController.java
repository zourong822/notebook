package com.company.springcloud.controller;

import com.company.springcloud.domain.CommonResult;
import com.company.springcloud.domain.Storage;
import com.company.springcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 11:51
 * @Description:
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;
    @PostMapping("/change")@GetMapping("/create")
    public CommonResult<Storage> changeStorage(Long productId,Integer count){
        storageService.storageChange(productId,count);
        return new CommonResult<>(200,"库存修改成功");
    }
}
