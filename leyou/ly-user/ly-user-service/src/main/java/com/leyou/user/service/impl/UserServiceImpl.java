package com.leyou.user.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import com.leyou.user.utils.CodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zourong
 * @Date: 2020/7/13 15:08
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final static String VERIFY_CODE_KEY="user:verify:phone:";

    @Override
    public Boolean checkData(String data, Integer type) {
        String cs = String.valueOf(type);

        if(StringUtils.isBlank(data) ||"null".equals(cs) || StringUtils.isBlank(cs)){
            throw new LyException(ExceptionEnum.INVALID_REQUEST_PARAM);
        }
        User user=new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_REQUEST_PARAM);
        }
        return userMapper.selectCount(user) == 0;
    }

    @Override
    public void sendVerifyCode(String phone) {
        String code= NumberUtils.generateCode(6);
        //发送短信
        Map<String,String > paramMap =new HashMap<>();
        paramMap.put("phone",phone);
        paramMap.put("code",code);
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",paramMap);
        //存入redis，用于验证
        redisTemplate.opsForValue().set(VERIFY_CODE_KEY+phone,code,5, TimeUnit.MINUTES);
    }

    @Override
    public void register(User user, String code) {
        //校验验证码
        String verifyCode = redisTemplate.opsForValue().get(VERIFY_CODE_KEY + user.getPhone());
        if(!StringUtils.equals(code,verifyCode)){
            throw new LyException(ExceptionEnum.REGISTER_PARAM_ERROR);
        }
        //生成盐
        String salt = CodeUtils.getSalt();
        user.setSalt(salt);
        //根据盐加密密码
        String md5Password = CodeUtils.md5PasswordAndSalt(user.getPassword(),salt);
        user.setPassword(md5Password);
        //注册
        user.setId(null);
        user.setCreated(new Date());
        userMapper.insert(user);
        //删除redis验证码缓存
        redisTemplate.delete(VERIFY_CODE_KEY + user.getPhone());
    }

    @Override
    public User query(String username, String password) {
        User user=new User();
        user.setUsername(username);
        User success = userMapper.selectOne(user);
        if(null == success){
            throw new LyException(ExceptionEnum.LOGIN_FAILED);
        }
        if(!StringUtils.equals(success.getPassword(),CodeUtils.md5PasswordAndSalt(password,success.getSalt()))){
            throw new LyException(ExceptionEnum.LOGIN_FAILED);
        }
        return success;
    }
}
