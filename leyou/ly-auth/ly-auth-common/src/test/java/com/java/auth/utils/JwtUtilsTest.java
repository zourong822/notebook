package com.java.auth.utils;

import com.java.auth.pojo.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class JwtUtilsTest {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private String pubPath="G:"+ File.separator+"TEMP"+ File.separator+"rsa.pub";
    private String priPath="G:"+ File.separator+"TEMP"+ File.separator+"rsa.pri";
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(pubPath,priPath,"ocean");
    }

    @Before
    public void getKyes() throws Exception {
        publicKey=RsaUtils.getPublicKey(pubPath);
        privateKey=RsaUtils.getPrivateKey(priPath);
        System.out.println("init method invoked");
    }

    @Test
    public void generateToken() throws Exception {
        String t = JwtUtils.generateToken(new UserInfo(30L, "张三"), privateKey, 5);
        System.out.println(t);
    }

    @Test
    public void readToken() throws Exception {
        UserInfo userInfo=JwtUtils.getInfoFromToken("eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MzIsInVzZXJuYW1lIjoidGVzdDEiLCJleHAiOjE1OTQ4ODcyNzl9.NMABT9j4G9095H7AwhVQQbCe-ZFCOcdTo7h2hwZlzQ4EBxqbGtKihrZWQ-10A6KPgT0yU7R4klqzV7FCWOHolJfu35vtfa9kfIXoZCRSyDfVsNoNmN69iGvklgDoMLuP1bpmy94Xx0KGAlgiaVWTzsylLgi7q_OOCHQ5N3laN4M",publicKey);
        System.out.println(userInfo);
    }
}