package org.halk.blog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HalkBlogWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalkBlogWebApplication.class, args);
	}

	/**
	 * 全局跨域过滤器配置
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//配置允许跨域访问的路径
				registry.addMapping("/**/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true)
						.exposedHeaders("")
						.maxAge(3600);
			}
		};
	}

}
