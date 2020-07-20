package com.leyou.cart.interceptor;

import com.java.auth.pojo.UserInfo;
import com.java.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zourong
 * @Date: 2020/7/17 15:31
 * @Description:
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties prop;

    public final static ThreadLocal<UserInfo> THREAD_LOCAL=new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request,prop.getCookieName());
        if(StringUtils.isBlank(token)){
            return false;
        }
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
        if(null == userInfo){
            return false;
        }
        THREAD_LOCAL.set(userInfo);
        return true;
    }

    /**
     * @Author zourong
     * @Description 从当前线程获取用户登录信息
     * @Date 2020/7/17 15:41
     * @Param []
     * @return com.java.auth.pojo.UserInfo
     **/
    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //因为tomcat使用的是线程池，该线程可能永远都不会结束，所以在处理完业务逻辑后需手动清理线程的局部变量
        THREAD_LOCAL.remove();
    }
}
