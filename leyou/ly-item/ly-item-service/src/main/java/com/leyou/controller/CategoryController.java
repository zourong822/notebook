package com.leyou.controller;

import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: zourong
 * @Date: 2020/6/4 17:11
 * @Description:
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @Author zourong
     * @Description 根据parentId查询商品分类
     * @Date 2020/6/4 17:18
     * @Param [pid]
     * @return java.util.List<com.leyou.item.pojo.Category>
     **/
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    /**
     * @Author zourong
     * @Description 根据分类id集合查询分类集合
     * @Date 2020/6/28 8:47
     * @Param [ids]
     * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
     **/
    @GetMapping("/list/ids")
    public ResponseEntity<List<Category>> queryListByIds(@RequestParam("ids") List<Long> ids){
        List<Category> cNames=categoryService.selectByIds(ids);
        return ResponseEntity.ok(cNames);
    }

}
