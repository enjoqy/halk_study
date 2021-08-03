package org.halk.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author halk
 * @Date 2021/2/1 11:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Service1Application {
    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class);
    }
}
