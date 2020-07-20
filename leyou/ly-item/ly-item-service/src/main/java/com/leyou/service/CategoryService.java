package com.leyou.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     * @Author zourong
     * @Description 根据parentId查询商品分类
     * @Date 2020/6/4 17:17
     * @Param [pid]
     * @return java.util.List<com.leyou.item.pojo.Category>
     **/
    List<Category> queryCategoryListByPid(Long pid);

    List<Category> selectByIds(List<Long> ids);
}
