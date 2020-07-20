package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Auther: zourong
 * @Date: 2020/6/17 18:35
 * @Description:
 */
@Data
@Table(name = "tb_sku")
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long spuId;

    private String title;

    private String images;

    private Long price;//价格，单位分

    private String indexes;

    private String ownSpec;//sku特有的规格参数键值对，json格式

    private Boolean enable;

    private Date createTime;

    private Date lastUpdateTime;

    @Transient
    private Integer stock;

}
