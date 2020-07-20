package com.company.springcloud.controller;

import com.company.springcloud.domain.Account;
import com.company.springcloud.domain.CommonResult;
import com.company.springcloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 11:51
 * @Description:
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/change")
    CommonResult<Account> accountChange(Long userId, BigDecimal money){
        accountService.changeMoney(userId,money);
        return new CommonResult<>(200,"余额扣除完毕");
    }
}
