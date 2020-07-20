package com.leyou.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Category;
import com.leyou.mapper.CategoryMapper;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zourong
 * @Date: 2020/6/4 17:11
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryListByPid(Long pid) {
        //通用mapper会把对象中不为空的属性值作为查询条件
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    @Override
    public List<Category> selectByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_IS_EMPTY);
        }
        return categories;
    }


}
