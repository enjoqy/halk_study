package org.halk.provider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author halk
 * @Date 2021/1/29 16:56
 */
@FeignClient("quickstart-consumer")
public interface ConsumerClient {

    /**
     * 消费者
     * @return
     */
    @GetMapping("/service")
    String service();

    @GetMapping("/info")
    String info();

}
