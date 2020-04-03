package com.company.spring;

import com.company.dao.AccountDao;
import com.company.entity.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {
    @Test
    public void testMyBatis() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
//        List<Account> list = accountDao.findAll();
//        for (Account account : list) {
//            System.out.println(account);
//        }
        Account account=new Account();
        account.setName("ddd");
        account.setMoney(666d);
        accountDao.save(account);
        sqlSession.commit();
        in.close();
        sqlSession.close();
    }
}
