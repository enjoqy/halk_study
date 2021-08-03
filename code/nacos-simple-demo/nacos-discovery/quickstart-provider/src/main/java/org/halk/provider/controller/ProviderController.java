package org.halk.provider.controller;

import org.halk.provider.client.ConsumerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产方
 *
 * @Author halk
 * @Date 2021/1/29 16:57
 */
@RestController
public class ProviderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private ConsumerClient consumerClient;

    @GetMapping("service")
    public String service() {
        LOGGER.info("provider invoke");
        return "provider invoke \t" + consumerClient.info();
    }

    @GetMapping("info")
    public String info() {
        LOGGER.info("provider info");
        return "provider info info info";
    }
}
