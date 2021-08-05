package org.junhi.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  // 开启zuul网关组件
@EnableDiscoveryClient  // 注册到eureka
public class HalkZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(HalkZuulApplication.class, args);
    }

}
