package org.halk.dispatcher_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author halk
 * @Description
 * @Date 2021/8/13 0013 16:48
 **/
@SpringBootApplication
@MapperScan("org.halk.*.mapper")
public class DispatcherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatcherServiceApplication.class, args);
    }

}
