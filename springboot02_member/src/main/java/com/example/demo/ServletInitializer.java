package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/*
 * SpringBoot 웹 어플리케이션을 배포할때는 주로 embeded tomcat이 내장된 jar파일을 사용한다.
 * 하지만 특별한 경우에는 전통적인 배포 방식인 war파일로 배포를 진행해야 하는 경우 이 
 * SpringBootServletInitializer을 상속받으면 된다.
 */

public class ServletInitializer  extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(Springboot02MemberApplication.class);
	}
 
}
