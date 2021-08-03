package org.halk.consumer.controller;

import org.halk.consumer.client.ProviderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author halk
 * @Date 2021/1/29 16:36
 */
@RestController
public class ConsumerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private ProviderClient providerClient;

    @GetMapping("/service")
    public String service(){
        LOGGER.info("provider invoke");
        return "consumer invoke hello welcome 111111111 \t" + providerClient.info();
    }

    @GetMapping("/info")
    public String info(){
        LOGGER.info("consumer info");
        return "consumer info info info";
    }
}
