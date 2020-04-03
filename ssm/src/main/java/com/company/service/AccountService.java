package com.company.service;

import com.company.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    void save(Account account);
}
