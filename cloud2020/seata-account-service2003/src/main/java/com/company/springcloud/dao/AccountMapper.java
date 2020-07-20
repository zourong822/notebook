package com.company.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
/*
 * @Author zourong
 * @Description 
 * @Date 2020/4/26 11:38
 **/
@Mapper
public interface AccountMapper {

    void changeMoney(@Param("userId") Long userId, @Param("money")BigDecimal money);
}
