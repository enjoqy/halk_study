package org.halk.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author halk
 * @Date 2021/1/29 17:03
 */
@FeignClient("quickstart-provider")
public interface ProviderClient {

    /**
     * 生产方的方法
     *
     * @return
     */
    @GetMapping("service")
    String service();

    @GetMapping("info")
    String info();
}
