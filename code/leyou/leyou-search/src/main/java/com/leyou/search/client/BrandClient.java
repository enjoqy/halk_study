package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/5/13 0013 11:37
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
