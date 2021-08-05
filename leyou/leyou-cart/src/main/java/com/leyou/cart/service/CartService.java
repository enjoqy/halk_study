package com.leyou.cart.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author halk
 * @Date 2020/6/4 0004 17:24
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static final String KEY_PREFIX = "user:cart:";

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    /**
     * @return void
     * @Author halk
     * @Description 加入购物车，redis中的数据结构为哈希结构，对应java中为双层map，Map<userId, Map<skuId, cart>> hash
     * @Date 2020/6/5 0005 11:01
     * @Param [cart]
     **/
    public void addCart(Cart cart) {

        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //查询购物车记录 ， string为存入redis的key，两个object为一个map，key为object，value为object
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();

        //判断当前的商品是否在购物车中
        if (hashOperations.hasKey(key)) {
            //在，更新数量
            String cartJson = hashOperations.get(key).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum() + num);
        } else {
            //不在，新增购物车
            Sku sku = this.goodsClient.querySkuById(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setSkuId(sku.getId());
            cart.setTitle(sku.getTitle());
            cart.setPrice(sku.getPrice());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
        }
        hashOperations.put(key, JsonUtils.serialize(cart));

    }

    /**
     * @return java.util.List<com.leyou.cart.pojo.Cart>
     * @Author halk
     * @Description 获取用户的购物车记录
     * @Date 2020/6/9 0009 17:19
     * @Param []
     **/
    public List<Cart> queryCarts() {

        //从线程变量中获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return null;
        }

        //从redis中获取用户的购物车记录，redis中存的数据结构为Map<userId, Map<skuId, cart>>
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        //获取购物车Map中所有Cart集合
        List<Object> cartsJson = hashOperations.values();

        //判断集合是否为空
        if (CollectionUtils.isEmpty(cartsJson)) {
            return null;
        }

        //将List<Object>转化为List<Cart>
        return cartsJson.stream().map(cart -> JsonUtils.parse(cart.toString(), Cart.class)).collect(Collectors.toList());
    }

    /**
     * @return void
     * @Author halk
     * @Description 更新购物车商品的数量
     * @Date 2020/6/10 0010 10:09
     * @Param [cart]
     **/
    public void updateNum(Cart cart) {

        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return;
        }

        Integer num = cart.getNum();

        //从redis中获取用户的购物车记录，redis中存的数据结构为Map<userId, Map<skuId, cart>>
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        //根据skuId获取对应的商品
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();

        cart = JsonUtils.parse(cartJson, Cart.class);

        //获取购物车中的商品记录重新设置值，再放入redis中
        cart.setNum(num);
        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    /**
     * @return void
     * @Author halk
     * @Description 删除购物车中的一个记录
     * @Date 2020/6/10 0010 15:18
     * @Param [cart]
     **/
    public void deleteCart(Cart cart) {

        UserInfo userInfo = LoginInterceptor.getUserInfo();

        //判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return;
        }

        //从redis中获取用户的购物车记录，redis中存的数据结构为Map<userId, Map<skuId, cart>>
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        //根据skuId删除
        hashOperations.delete(cart.getSkuId().toString());
    }

    /**
     * @return void
     * @Author halk
     * @Description 批量删除购物车中的记录
     * @Date 2020/6/10 0010 16:16
     * @Param [skuIds]
     **/
    public void deleteCartList(String skuIds) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(skuIds).get("skuIds");
            if (jsonNode.isArray()) {
                jsonNode.forEach(skuId -> {
                    Cart cart = new Cart();
                    cart.setSkuId(Long.parseLong(skuId.toString()));
                    this.deleteCart(cart);
                });
            }
        } catch (IOException e) {
            LOGGER.info("购物车批量删除请求参数解析错误：{}" + e, skuIds);
            e.printStackTrace();
        }
    }
}

