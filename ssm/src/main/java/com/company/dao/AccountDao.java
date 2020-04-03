package com.company.dao;

import com.company.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao {
    List<Account> findAll();
    void save(Account account);
}
