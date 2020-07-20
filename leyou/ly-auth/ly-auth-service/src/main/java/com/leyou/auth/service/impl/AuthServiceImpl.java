package com.leyou.auth.service.impl;

import com.java.auth.pojo.UserInfo;
import com.java.auth.utils.JwtUtils;
import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Auther: zourong
 * @Date: 2020/7/16 10:52
 * @Description:
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtProperties prop;

    @Autowired
    private UserClient userClient;
    @Override
    public String accredit(String username, String password) {
        //1.校验登录
        User user = userClient.queryUser(username, password);
        //2.生成token并返回
        try {
            return JwtUtils.generateToken(new UserInfo(user.getId(),user.getUsername()),prop.getPrivateKey(),prop.getExpire());
        } catch (Exception e) {
            log.error("[授权中心]生成token时发生异常",e);
        }
        return null;
    }
}
