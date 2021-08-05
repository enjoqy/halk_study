package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心，可以是eureka，也可以是其他的
 * @Author halk
 * @Date 2019/12/28 19:24
 */
@SpringBootApplication
@EnableEurekaServer
public class LeyouRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouRegistryApplication.class);
    }
}
