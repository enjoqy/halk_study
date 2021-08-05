package com.leyou.cart.pojo;

import lombok.Data;
/**
 * @Author halk
 * @Description 购物车的实体类
 * @Date 2020/6/4 0004 17:24
 * @Param
 * @return
 **/
@Data
public class Cart {
    /**
     *  用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     *  标题
     */
    private String title;
    /**
     * 图片
     */
    private String image;
    /**
     * 加入购物车时的价格
     */
    private Long price;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 商品规格参数
     */
    private String ownSpec;

}
