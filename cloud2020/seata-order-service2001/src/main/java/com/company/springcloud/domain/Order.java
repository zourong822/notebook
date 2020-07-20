package com.company.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Auther: zourong
 * @Date: 2020/4/24 10:38
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**订单未完成状态0*/
    public static final Integer UNFINISHED=0;
    /**订单已完成状态1*/
    public static final Integer FINISHED=1;
    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal money;
    //订单状态0：创建中，1：已完成
    private Integer status;
}
