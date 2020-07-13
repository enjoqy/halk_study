package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/6/8 0008 11:34
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
