package com.company.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zourong
 * @Date: 2020/4/26 08:47
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    private Long id;
    /**商品id*/
    private Long productId;
    /**总库存*/
    private Integer total;
    /**已售出*/
    private Integer used;
    /**剩余库存*/
    private Integer residue;
}
