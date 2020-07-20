package com.company.springcloud.service;

import com.company.springcloud.dao.OrderMapper;
import com.company.springcloud.domain.Order;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 11:04
 * @Description:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StorageService storageService;
    @Autowired
    AccountService accountService;
    @Override
    @GlobalTransactional
    public Order createOrder(Order order) {
        log.info("==============>begin创建订单");
        orderMapper.createOrder(order);
        log.info("==============>end创建订单");

        log.info("==============>begin调用微服务减少库存");
        storageService.storageChange(order.getProductId(),order.getCount());
        log.info("==============>end调用微服务减少库存");

        log.info("==============>begin调用微服务扣除款项");
        accountService.accountChange(order.getUserId(),order.getMoney());
        log.info("==============>end调用微服务扣除款项");

        log.info("==============>begin修改订单状态为已完成");
        orderMapper.changeOrderStatus(order.getId(),Order.UNFINISHED,Order.FINISHED);
        log.info("==============>end修改订单状态为已完成");
        log.info("order:{}",order.toString());
        return order;
    }
}
