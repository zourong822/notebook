package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Auther: zourong
 * @Date: 2020/4/27 17:39
 * @Description:针对controller的通知，全局异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResult> LyExceptionHandler(LyException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
