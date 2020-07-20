package com.leyou.item.api;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    /**
     * @Author zourong
     * @Description 分页查询spu
     * @Date 2020/6/28 9:17
     * @Param [key, saleable, page, rows]
     * @return com.leyou.common.vo.PageResult<com.leyou.item.pojo.Spu>
     **/
    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
    );

    /**
     * @Author zourong
     * @Description 根据spuId查询商品详情
     * @Date 2020/6/28 9:17
     * @Param
     * @return
     **/
    @GetMapping("/spu/detail/{spuId}")
    SpuDetail queryDetailBySpuId(@PathVariable("spuId")Long spuId);

    /**
     * @Author zourong
     * @Description 根据spuId查询sku
     * @Date 2020/6/28 9:18
     * @Param [spuId]
     * @return java.util.List<com.leyou.item.pojo.Sku>
     **/
    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);

    /**
     * @Author zourong
     * @Description 根据spuId查询spu
     * @Date 2020/7/5 17:46
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Spu>
     **/
    @PutMapping("/spu/id")
    Spu queryGoodsBySpuId(@RequestParam("id") Long id);
}
