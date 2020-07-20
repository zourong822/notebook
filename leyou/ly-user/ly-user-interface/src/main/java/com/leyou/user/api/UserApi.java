package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    /**
     * @Author zourong
     * @Description 根据用户名和密码查询，用于登录
     * @Date 2020/7/14 11:38
     * @Param [username, password]
     * @return org.springframework.http.ResponseEntity<com.leyou.user.pojo.User>
     **/
    @PostMapping("/query")
    User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
