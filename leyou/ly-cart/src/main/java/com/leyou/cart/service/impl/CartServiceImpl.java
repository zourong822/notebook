package com.leyou.cart.service.impl;

import com.java.auth.pojo.UserInfo;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zourong
 * @Date: 2020/7/17 16:27
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static String CART_KEY="cart:user:";

    @Override
    public void addCart(Cart cart) {
        //获取用户id
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //查询redis该商品是否已经存在
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(CART_KEY + userInfo.getId());
        Object o = hashOps.get(cart.getSkuId().toString());
        int num=cart.getNum();
        if(null != o){
            //存在则更新数量
            cart= JsonUtils.parse(o.toString(),Cart.class);
            cart.setNum(cart.getNum()+num);
        }
        //前台传过来的cart没有userID，补上
        cart.setUserId(userInfo.getId());
        //更新或保存
        hashOps.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    @Override
    public List<Cart> getCarts() {
        //获取用户id
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        Boolean hasKey = redisTemplate.hasKey(CART_KEY + userInfo.getId());
        if(!hasKey){
            //不存在该key
            throw new LyException(ExceptionEnum.CART_IS_EMPTY);
        }
        //查询redis该商品是否已经存在
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(CART_KEY + userInfo.getId());


        if(null == hashOps ||hashOps.size()==0){
            //不存在数据
            throw new LyException(ExceptionEnum.CART_IS_EMPTY);
        }
        List<Object> carts = hashOps.values();
        return  carts.stream().map(cart -> JsonUtils.parse(cart.toString(), Cart.class)).collect(Collectors.toList());

    }

    @Override
    public List<Cart> syncCartAndQuery(List<Cart> carts) {
        for (Cart cart : carts) {
            addCart(cart);
        }
        return getCarts();
    }

    @Override
    public void editCartNum(Cart cart) {
        //获取用户id
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        Boolean hasKey = redisTemplate.hasKey(CART_KEY + userInfo.getId());
        if(!hasKey){
            //不存在该key
            throw new LyException(ExceptionEnum.CART_IS_EMPTY);
        }
        //查询redis该商品是否已经存在
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(CART_KEY + userInfo.getId());
        Object o = hashOps.get(cart.getSkuId().toString());
        int num=cart.getNum();
        if(null != o){
            //存在则更新数量
            cart= JsonUtils.parse(o.toString(),Cart.class);
            cart.setNum(num);
        }else{
            throw new LyException(ExceptionEnum.CART_IS_EMPTY);
        }
        //更新或保存
        hashOps.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    @Override
    public void delCart(Long skuId) {
        //获取用户id
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        Boolean hasKey = redisTemplate.hasKey(CART_KEY + userInfo.getId());
        if(!hasKey){
            //不存在该key
            throw new LyException(ExceptionEnum.CART_IS_EMPTY);
        }
        //删除
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(CART_KEY + userInfo.getId());
        hashOps.delete(skuId.toString());
    }
}
