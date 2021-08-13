package org.halk.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author halk
 * @Description  swagger配置
 * @Date 2021/8/13 0013 10:54
 **/
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("订单服务 RESTful APIs")
                        .description("# 订单服务 RESTful APIs")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("halk", "", "gdlzhh321@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("订单服务")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("org.halk.order_service.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
