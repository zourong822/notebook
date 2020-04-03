package com.company.service.impl;

import com.company.dao.AccountDao;
import com.company.entity.Account;
import com.company.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Override
    public List<Account> findAll() {
        System.out.println("Service.findAll()....");
        return accountDao.findAll();
    }

    @Override
    public void save(Account account) {
        System.out.println("Service.save()....");

        accountDao.save(account);
        int i=10/0;
        accountDao.save(account);
    }
}
