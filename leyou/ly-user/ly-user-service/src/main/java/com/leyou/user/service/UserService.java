package com.leyou.user.service;

import com.leyou.user.pojo.User;

public interface UserService {
    Boolean checkData(String data, Integer type);

    void sendVerifyCode(String phone);

    void register(User user, String code);

    User query(String username, String password);
}
