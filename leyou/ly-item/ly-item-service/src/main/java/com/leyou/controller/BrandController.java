package com.leyou.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.service.BrandService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/6/12 13:19
 * @Description:
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param keyWord 搜索关键字
     * @param desc 是否降序
     * @param page 第几页
     * @param PageSize 当前页大小
     * @param sortBy 排序字段
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandListByPid(@RequestParam(value = "keyWord",required = false)String keyWord,
                                                                 @RequestParam(value = "desc",defaultValue = "false")Boolean desc,
                                                                 @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                                 @RequestParam(value = "PageSize",defaultValue = "5")Integer pageSize,
                                                                 @RequestParam(value = "sortBy",required = false )String sortBy){
        return ResponseEntity.ok(brandService.queryBrandPage(keyWord,desc,page,pageSize,sortBy));
    }

    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.addBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Author zourong
     * @Description 根据分类id查询品牌
     * @Date 2020/6/28 9:43
     * @Param [cid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Brand>>
     **/
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * @Author zourong
     * @Description 根据品牌id查询
     * @Date 2020/6/28 9:45
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    /**
     * @Author zourong
     * @Description 根据品牌id集合查询
     * @Date 2020/6/30 20:56
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("/list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.getBrandByIds(ids));
    }

}
