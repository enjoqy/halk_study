package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 * @Author halk
 * @Date 2020/6/4 0004 17:24
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * @Author halk
     * @Description 加入购物车
     * @Date 2020/6/5 0005 11:01
     * @Param [cart]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Author halk
     * @Description 查询购物车
     * @Date 2020/6/9 0009 17:19
     * @Param []
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.cart.pojo.Cart>>
     **/
    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts(){
        List<Cart> carts = this.cartService.queryCarts();
        if (CollectionUtils.isEmpty(carts)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * @Author halk
     * @Description 更新购物车商品的数量
     * @Date 2020/6/10 0010 10:09
     * @Param [cart]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PutMapping
    public ResponseEntity<Void> UpdateNum(@RequestBody Cart cart){
        this.cartService.updateNum(cart);
        return ResponseEntity.noContent().build();
    }

    /**
     * @Author halk
     * @Description 删除购物车中的一个记录
     * @Date 2020/6/10 0010 15:18
     * @Param [cart]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestBody Cart cart){
        this.cartService.deleteCart(cart);
        return ResponseEntity.noContent().build();
    }

    /**
     * @Author halk
     * @Description 批量删除购物车中的记录
     * @Date 2020/6/10 0010 15:18
     * @Param [cart]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteCartList(@RequestBody String skuIds){
        this.cartService.deleteCartList(skuIds);
        return ResponseEntity.noContent().build();
    }



}
