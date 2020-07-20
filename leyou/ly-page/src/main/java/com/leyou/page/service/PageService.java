package com.leyou.page.service;

import java.util.Map;

public interface PageService {
    Map<String,Object> queryGoodsDetail(Long id);

    /**
     * @Author zourong
     * @Description 商品新增或修改，重新生成静态化页面
     * @Date 2020/7/8 10:33
     * @Param [spuId]
     * @return void
     **/
    void insertOrUpdatePage(Long spuId);

    /**
     * @Author zourong
     * @Description 商品删除，删除对应静态化页面
     * @Date 2020/7/8 10:34
     * @Param [spuId]
     * @return void
     **/
    void deletePage(Long spuId);
}
