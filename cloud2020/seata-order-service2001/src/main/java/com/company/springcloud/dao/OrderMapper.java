package com.company.springcloud.dao;

import com.company.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    /*
     * @Author zourong
     * @Description 创建订单
     * @Date 2020/4/24 10:44
     * @Param [order]
     * @return void
     **/
    void createOrder(Order order);

    /*
     * @Author zourong
     * @Description 修改订单状态，0为未完成，1为已完成
     * @see com.company.springcloud.domain.Order
     * @Date 2020/4/24 10:44
     * @Param [orderId, before,after]
     * @return void
     **/
    void changeOrderStatus(@Param("orderId") Long orderId,
                           @Param("before") Integer before,
                           @Param("after") Integer after);
}
