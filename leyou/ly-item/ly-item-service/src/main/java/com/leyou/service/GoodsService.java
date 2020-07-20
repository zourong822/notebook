package com.leyou.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

public interface GoodsService {
    /**
     * @Author zourong
     * @Description 分页查询商品spu列表
     * @Date 2020/6/17 15:29
     * @Param [key, saleable, page, rows]
     * @return com.leyou.common.vo.PageResult<com.leyou.item.pojo.Spu>
     **/
    PageResult<Spu> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    void addGoods(Spu spu);

    SpuDetail queryDetialBySpuId(Long spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void updateGoods(Spu spu);

    Spu queryGoodsBySpuId(Long id);
}
