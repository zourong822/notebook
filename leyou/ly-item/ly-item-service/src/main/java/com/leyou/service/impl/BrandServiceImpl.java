package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.mapper.BrandMapper;
import com.leyou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/6/12 13:33
 * @Description:
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public PageResult<Brand> queryBrandPage(String keyWord, Boolean desc, Integer page, Integer pageSize, String sortBy) {
        //分页
        PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Brand.class);
        if(StringUtils.isNotBlank(keyWord)){
            example.createCriteria().orLike("name","%"+keyWord+"%")
                    .orEqualTo("letter",keyWord.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+(desc?" DESC":" ASC"));
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_IS_EMPTY);
        }
        //总页数
        PageInfo<Brand> pageInfo=new PageInfo<>(brands);

        return new PageResult<>(pageInfo.getTotal(),brands);
    }

    @Override
    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int insert = brandMapper.insert(brand);
        if(insert!=1){
            throw new LyException(ExceptionEnum.ADD_BRAND_FAILED);
        }
        for (Long cid : cids) {
            int result = brandMapper.addCategoryBrand(brand.getId(), cid);
            if(result !=1){
                throw new LyException(ExceptionEnum.ADD_BRAND_FAILED);
            }
        }
    }

    @Override
    public Brand getBrandById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if(null == brand){
            throw new LyException(ExceptionEnum.BRAND_IS_EMPTY);
        }
        return brand;
    }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_IS_EMPTY);
        }
        return list;
    }

    /**
     * @Author zourong
     * @Description 根据品牌id集合查询
     * @Date 2020/6/30 20:57
     * @Param [ids]
     * @return java.util.List<com.leyou.item.pojo.Brand>
     **/
    @Override
    public List<Brand> getBrandByIds(List<Long> ids) {
        List<Brand> list = brandMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_IS_EMPTY);
        }
        return list;
    }


}
