package org.halk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author halk
 * @Date 2020/5/22 0022 14:46
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouGoodsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouGoodsWebApplication.class);
    }
}
