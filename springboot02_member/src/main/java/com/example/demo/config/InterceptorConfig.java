package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.common.interceptor.ViewNameInterceptor;

@Configuration
//public class InterceptorConfig extends WebMvcConfigurerAdapter {
public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ViewNameInterceptor()).addPathPatterns("/*.do").addPathPatterns("/*/*.do")
				.excludePathPatterns("/users/login");
	}

	

}
