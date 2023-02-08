package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.security.service.CorsRefFilter;


//SpringBootServletInitializer : 배포할때 사용

@SpringBootApplication
public class Springsecurity04ShopJwtApplication  extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(Springsecurity04ShopJwtApplication.class, args);
		
//		 final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//		    annotationConfigApplicationContext.register(CorsRefFilter.class);
//		    annotationConfigApplicationContext.refresh();
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(Springsecurity04ShopJwtApplication.class);
	}

}
