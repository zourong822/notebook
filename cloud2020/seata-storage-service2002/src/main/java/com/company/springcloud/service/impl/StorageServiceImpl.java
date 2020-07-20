package com.company.springcloud.service.impl;

import com.company.springcloud.dao.StorageMapper;
import com.company.springcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zourong
 * @Date: 2020/4/26 08:59
 * @Description:
 */
@Service
public class StorageServiceImpl implements StorageService{
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void storageChange(Long productId, Integer count) {
        storageMapper.storageChange(productId,count);
    }
}
