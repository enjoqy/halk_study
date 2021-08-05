package org.halk.application1.controller;

import org.halk.service1.api.ConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author halk
 * @Date 2021/1/31 10:32
 */
@RestController
public class Application1Controller {

    /**
     * 注入service，基于dubbo协议
     */
    @org.apache.dubbo.config.annotation.Reference
    private ConsumerService consumerService;

    @GetMapping("/service")
    public String service() {
        return "application1" + consumerService.service();
    }
}
