package com.company.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;

/**
 * @Auther: zourong
 * @Date: 2020/4/22 10:48
 * @Description: sentinel全局兜底类
 */
public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException e){
        return new CommonResult(502,"global handlerException----1"+(e==null?"":e.getMessage()),new Payment(2020L,"serial007"));
    }

    public static CommonResult handlerException2(BlockException e){
        return new CommonResult(502,"global handlerException----2"+(e==null?"":e.getMessage()),new Payment(2020L,"serial007"));
    }
}
