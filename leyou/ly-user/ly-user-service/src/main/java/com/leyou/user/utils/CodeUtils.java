package com.leyou.user.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Auther: zourong
 * @Date: 2020/7/14 10:59
 * @Description:
 */
public class CodeUtils {
    /**
     * @Author zourong
     * @Description 生成随机盐
     * @Date 2020/7/14 11:02
     * @Param []
     * @return java.lang.String
     **/
    public static String getSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /**
     * @Author zourong
     * @Description 和盐一起加密密码
     * @Date 2020/7/14 11:02
     * @Param
     * @return
     **/
    public static String md5PasswordAndSalt(String password,String salt){
        String code=password + salt;
        return DigestUtils.md5DigestAsHex(code.getBytes());
    }
}
