package com.leyou.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface BrandService {
    /**
     * 分页查询品牌
     * @param keyWord 搜索关键字
     * @param desc 是否降序
     * @param page 第几页
     * @param pageSize 当前页大小
     * @param sortBy 排序字段
     * @return
     */
    PageResult<Brand> queryBrandPage(String keyWord,
                                     Boolean desc,
                                     Integer page,
                                     Integer pageSize,
                                     String sortBy);

    /**
     * @Author zourong
     * @Description 添加品牌
     * @Date 2020/6/12 15:52
     * @Param [brand, cids]
     * @return void
     **/
    void addBrand(Brand brand, List<Long> cids);

    Brand getBrandById(Long id);

    List<Brand> queryBrandByCid(Long cid);

    List<Brand> getBrandByIds(List<Long> ids);
}
