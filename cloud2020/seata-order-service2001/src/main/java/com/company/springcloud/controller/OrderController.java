package com.company.springcloud.controller;

import com.company.springcloud.domain.CommonResult;
import com.company.springcloud.domain.Order;
import com.company.springcloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 11:51
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/change")
    public CommonResult<Order> change(Order order){
        Order result = orderService.createOrder(order);
        return new CommonResult<>(200,"创建订单成功",result);
    }
}
