package com.leyou.pojo;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zourong
 * @Date: 2020/6/29 21:07
 * @Description:
 */
@Data
public class SearchResult<T> extends PageResult<T> {
    //分类集合
    private List<Category> categories;
    //品牌集合
    private List<Brand> brands;

    //分类规格参数可搜索集合
    private List<Map<String,Object>> specs;

    public SearchResult() {
    }


}
