package com.leyou.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {

    @Insert("INSERT INTO tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    int addCategoryBrand(@Param("bid")Long bid,@Param("cid")Long cid);

    @Select("SELECT * FROM tb_brand b INNER JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id=#{cid}")
    List<Brand> queryBrandByCid(@Param("cid")Long cid);
}
