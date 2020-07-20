package com.leyou.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/6/17 15:25
 * @Description:
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(key,saleable,page,rows));
    }

    @PostMapping("/spu/goods")
    public ResponseEntity<Void> addGoods(@RequestBody Spu spu){
        goodsService.addGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> queryDetailBySpuId(@PathVariable("spuId")Long spuId){
        return ResponseEntity.ok(goodsService.queryDetialBySpuId(spuId));
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }

    @PutMapping("/spu/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @Author zourong
     * @Description 根据spuId查询spu
     * @Date 2020/7/5 17:46
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Spu>
     **/
    @PutMapping("/spu/id")
    public ResponseEntity<Spu> queryGoodsBySpuId(@RequestParam("id") Long id){
        Spu spu =goodsService.queryGoodsBySpuId(id);
        return ResponseEntity.ok(spu);
    }
}
