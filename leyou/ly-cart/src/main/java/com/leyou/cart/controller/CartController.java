package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/7/17 16:26
 * @Description:
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> editCartNum(@RequestBody Cart cart){
        cartService.editCartNum(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{skuId}")
    public ResponseEntity<Void> delCart(@PathVariable("skuId") Long skuId){
        cartService.delCart(skuId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts(){
        List<Cart> carts=cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/syncCartAndQuery")
    public ResponseEntity<List<Cart>> syncCartAndQuery(@RequestBody List<Cart> carts){
        List<Cart> result=cartService.syncCartAndQuery(carts);
        return ResponseEntity.ok(result);
    }
}
