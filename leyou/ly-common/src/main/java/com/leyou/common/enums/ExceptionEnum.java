package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Auther: zourong
 * @Date: 2020/4/28 09:38
 * @Description: 全局异常类型枚举类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    PRICE_CONNOT_BE_NULL(400,"商品价格不能为空"),
    CATEGORY_IS_EMPTY(404,"商品分类未找到"),
    BRAND_IS_EMPTY(404,"品牌未找到" ),
    GOODS_IS_EMPTY(404,"商品未找到" ),
    UPDATE_GOODS_FAILED(404,"商品未找到" ),
    GOODS_ID_CANNOT_BE_NULL(400,"商品id不能为空" ),
    GOODS_DETAIL_IS_EMPTY(404,"商品详情未找到" ),
    GOODS_SKU_IS_EMPTY(404,"商品SKU未找到" ),
    GOODS_STOCK_IS_EMPTY(404,"商品库存未找到" ),
    SPEC_GROUP_IS_EMPTY(404,"规格组未找到" ),
    SPEC_PARAM_IS_EMPTY(404,"规格参数未找到" ),
    ADD_BRAND_FAILED(500,"添加品牌失败" ),
    ADD_GOODS_FAILED(500,"添加品牌失败" ),
    AUTH_ACCREDIT_FAILED(401,"授权失败" ),
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    INVALID_REQUEST_PARAM(400, "参数有误"),
    CART_IS_EMPTY(404, "购物车为空"),
    REGISTER_PARAM_ERROR(400, "参数有误,注册失败"),
    LOGIN_FAILED(400, "用户名或密码错误");
    private int code;
    private String msg;
}
