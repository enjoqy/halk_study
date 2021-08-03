package org.halk.application1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author halk
 * @Date 2021/1/31 10:31
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application1Application {
    public static void main(String[] args) {
        SpringApplication.run(Application1Application.class);
    }
}
