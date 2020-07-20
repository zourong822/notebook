package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BrandApi {
    /**
     * @Author zourong
     * @Description 根据品牌id查询
     * @Date 2020/6/28 9:45
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

    /**
     * @Author zourong
     * @Description 根据品牌id集合查询
     * @Date 2020/6/30 20:56
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("/brand/list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
