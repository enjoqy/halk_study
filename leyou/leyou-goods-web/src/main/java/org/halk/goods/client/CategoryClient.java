package org.halk.goods.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/5/13 0013 11:38
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
