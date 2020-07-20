package com.company.springcloud.service.impl;

import com.company.springcloud.dao.AccountMapper;
import com.company.springcloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zourong
 * @Date: 2020/4/26 11:36
 * @Description:
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void changeMoney(Long userId, BigDecimal money) {
        accountMapper.changeMoney(userId,money);
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
