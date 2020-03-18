package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	/** 定义识图控制器 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index/index").setViewName("index/index");
		registry.addViewController("/user/loginview").setViewName("users/loginview");
		registry.addViewController("/index/faliure").setViewName("index/faliure");
		registry.addViewController("/index/success").setViewName("index/success");
	}
	
}
