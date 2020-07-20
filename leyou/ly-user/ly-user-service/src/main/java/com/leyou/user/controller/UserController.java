package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Auther: zourong
 * @Date: 2020/7/13 15:08
 * @Description:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author zourong
     * @Description 校验用户名或手机是否可用
     * @Date 2020/7/14 9:50
     * @Param [data, type]
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
     **/
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer  type){
        return ResponseEntity.ok(userService.checkData(data,type));
    }

    /**
     * @Author zourong
     * @Description 发送验证码
     * @Date 2020/7/14 9:50
     * @Param
     * @return
     **/
    @GetMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @Author zourong
     * @Description 注册用户
     * @Date 2020/7/14 9:50
     * @Param [user, code]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code")String code){
        userService.register(user,code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Author zourong
     * @Description 根据用户名和密码查询，用于登录
     * @Date 2020/7/14 11:38
     * @Param [username, password]
     * @return org.springframework.http.ResponseEntity<com.leyou.user.pojo.User>
     **/
    @PostMapping("/query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        return ResponseEntity.ok(userService.query(username,password));
    }
}
