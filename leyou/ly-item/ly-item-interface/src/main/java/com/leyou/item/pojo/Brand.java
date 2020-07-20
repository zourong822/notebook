package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: zourong
 * @Date: 2020/6/12 13:19
 * @Description:
 */
@Table(name="tb_brand")
@Data
public class Brand {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private String name;
    private String image;
    private Character letter;
}
