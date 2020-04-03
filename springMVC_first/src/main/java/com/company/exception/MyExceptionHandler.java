package com.company.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MyException me=null;
        if(e instanceof MyException){
            me=(MyException)e;
        }else{
            me=new MyException("服务器正在维护。。。");
        }
        ModelAndView mav=new ModelAndView();
        mav.addObject("errorMsg",me.getErrorMsg());
        mav.setViewName("error");
        return mav;
    }
}
