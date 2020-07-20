package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

public interface CartService {
    void addCart(Cart cart);

    List<Cart> getCarts();

    List<Cart> syncCartAndQuery(List<Cart> carts);

    void editCartNum(Cart cart);

    void delCart(Long skuId);
}
