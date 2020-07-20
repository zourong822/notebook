package com.leyou.user.service.impl;

import com.leyou.user.utils.CodeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: zourong
 * @Date: 2020/7/14 11:54
 * @Description:
 */
public class Md5Test {
    public static void main(String[] args) {
        boolean b = StringUtils.equals("20d3ddf24d0e11d2feb6aa3c431e6961", CodeUtils.md5PasswordAndSalt("123456", "e1b7dd26f29c4a6ebf0b64215fb33920"));
        System.out.println(b);
    }
}
