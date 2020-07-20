package com.leyou.filter;

import com.java.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.cart.config.FilterConfig;
import com.leyou.cart.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/7/16 16:32
 * @Description:
 */
@EnableConfigurationProperties({JwtProperties.class, FilterConfig.class})
@Component
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtProperties prop;

    @Autowired
    private FilterConfig filterProp;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();

        List<String> allowPaths = filterProp.getAllowPaths();
        for (String allowPath : allowPaths) {
            if(StringUtils.contains(requestURI,allowPath)){
                return false;
            }
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        //校验登录
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        if(StringUtils.isBlank(token)){
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        try {
            //解析token失败会抛出异常
            JwtUtils.getInfoFromToken(token,prop.getPublicKey());
        } catch (Exception e) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
