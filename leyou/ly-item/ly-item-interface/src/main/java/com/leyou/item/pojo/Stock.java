package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: zourong
 * @Date: 2020/6/18 10:59
 * @Description:
 */
@Data
@Table(name = "tb_stock")
public class Stock {
    @Id
    private Long skuId;
    private Integer seckill_stock;//可秒杀库存
    private Integer seckill_total;//秒杀总库存
    private Integer stock;//库存
}
