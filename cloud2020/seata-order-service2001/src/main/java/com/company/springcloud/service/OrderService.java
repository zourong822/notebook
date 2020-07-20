package com.company.springcloud.service;

import com.company.springcloud.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService {
    /*
     * @Author zourong
     * @Description 创建订单
     * @Date 2020/4/24 10:44
     * @Param [order]
     * @return void
     **/
    Order createOrder(Order order);
}
