package com.company.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("seata-storage-service")
public interface StorageService {
    @PostMapping("/storage/change")
    void storageChange(@RequestParam("productId") Long productId,@RequestParam("count")Integer count);
}
