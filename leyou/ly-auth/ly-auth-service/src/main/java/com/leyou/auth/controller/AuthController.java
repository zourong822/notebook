package com.leyou.auth.controller;

import com.java.auth.pojo.UserInfo;
import com.java.auth.utils.JwtUtils;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zourong
 * @Date: 2020/7/16 10:39
 * @Description:
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class AuthController {

    @Autowired
    private JwtProperties prop;
    @Autowired
    private AuthService authService;

    /**
     * @Author zourong
     * @Description 校验登录，生成token存入cookie
     * @Date 2020/7/16 10:56
     * @Param [username, password, request, response]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PostMapping("/accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username")String username, @RequestParam("password")String password,
                                         HttpServletRequest request, HttpServletResponse response){
        String token =authService.accredit(username,password);
        if(StringUtils.isBlank(token)){
            throw new LyException(ExceptionEnum.AUTH_ACCREDIT_FAILED);
        }
        CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getCookieMaxAge());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN")String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        try {
            UserInfo userInfo= JwtUtils.getInfoFromToken(token,prop.getPublicKey());
            if(null == userInfo){
                throw new LyException(ExceptionEnum.AUTH_ACCREDIT_FAILED);
            }
            //更新token过期时间
            token=JwtUtils.generateToken(userInfo,prop.getPrivateKey(),prop.getExpire());
            //更新cookie过期时间
            CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getCookieMaxAge());
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            log.error("[授权中心]解析用户token失败",e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
