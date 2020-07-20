package com.company.springcloud.service;

import java.math.BigDecimal;

/**
 * @Author zourong
 * @Description 
 * @Date 2020/4/26 11:35
 **/
public interface AccountService {
    void changeMoney(Long userId, BigDecimal money);
}
