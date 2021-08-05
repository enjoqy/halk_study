package org.junhi.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // 启用eureka客户端
public class HalkServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HalkServiceProviderApplication.class, args);
    }

}
