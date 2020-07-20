package com.company.springcloud.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Auther: zourong
 * @Date: 2020/4/26 08:57
 * @Description:
 */
public interface StorageMapper {
    /**
     * @Author zourong
     * @Description 修改库存
     * @Date 2020/4/26 8:55
     * @Param [productId, count]
     * @return void
     **/
    void storageChange(@Param("productId") Long productId,@Param("count") Integer count);
}
