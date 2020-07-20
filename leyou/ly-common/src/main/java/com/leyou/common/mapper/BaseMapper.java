package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BaseMapper<T> extends Mapper<T>,InsertListMapper<T>,SelectByIdListMapper<T,Long>,DeleteByIdListMapper<T,Long>{
}
