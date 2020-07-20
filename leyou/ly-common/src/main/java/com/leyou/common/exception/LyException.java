package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zourong
 * @Date: 2020/4/28 09:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
