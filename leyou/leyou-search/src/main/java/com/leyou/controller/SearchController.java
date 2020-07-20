package com.leyou.controller;

import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zourong
 * @Date: 2020/6/28 16:14
 * @Description:
 */
@RestController
public class SearchController {

    @Autowired
    private GoodsService goodsService;
    /**
     * @Author zourong
     * @Description 分页搜索商品
     * @Date 2020/6/28 16:18
     * @Param []
     * @return org.springframework.http.ResponseEntity<com.leyou.common.vo.PageResult<com.leyou.pojo.Goods>>
     **/
    @PostMapping("/page")
    public ResponseEntity<SearchResult<Goods>> querySearchPage(@RequestBody SearchRequest request){
        return ResponseEntity.ok(goodsService.querySearchPage(request));
    }
}
