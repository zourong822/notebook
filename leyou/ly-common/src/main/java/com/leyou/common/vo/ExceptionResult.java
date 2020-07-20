package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: zourong
 * @Date: 2020/4/28 09:49
 * @Description:
 */
@Data
public class ExceptionResult {
    private int status;
    private String message;
    private long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        status=em.getCode();
        message=em.getMsg();
        timestamp=new Date().getTime();
    }
}
