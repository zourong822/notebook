package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Table(name = "tb_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    @Length(min=4,max=30,message = "用户名长度必须在4-30之间")
    private String username;// 用户名

    @JsonIgnore
    @Length(min=4,max=30,message = "密码长度必须在4-30之间")
    private String password;// 密码

    @Pattern(regexp = "^1[356789]\\d{9}$",message = "请输入正确的手机号码")
    private String phone;// 电话

    private Date created;// 创建时间

    @JsonIgnore
    private String salt;// 密码的盐值
}