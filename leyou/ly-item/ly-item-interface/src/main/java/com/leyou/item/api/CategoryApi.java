package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
public interface CategoryApi {
    /**
     * @Author zourong
     * @Description 根据分类id集合查询分类名称集合
     * @Date 2020/6/28 8:47
     * @Param [ids]
     * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
     **/
    @GetMapping("/category/list/ids")
    List<Category> queryListByIds(@RequestParam("ids") List<Long> ids);
}
