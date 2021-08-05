package org.halk.goods.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/5/13 0013 11:39
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
