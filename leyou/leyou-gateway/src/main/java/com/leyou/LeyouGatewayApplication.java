package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关代理服务
 * @Author halk
 * @Date 2019/12/28 19:39
 */
@SpringBootApplication
@EnableDiscoveryClient  // 启用eureka注册中心
@EnableZuulProxy  // 启用网关代理
public class LeyouGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouGatewayApplication.class);
    }
}
