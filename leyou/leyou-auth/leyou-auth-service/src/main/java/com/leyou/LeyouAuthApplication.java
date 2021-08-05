package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author halk
 * @Date 2020/6/1 0001 16:10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouAuthApplication.class);
    }
}
