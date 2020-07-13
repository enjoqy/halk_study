package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/5/13 0013 11:17
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
