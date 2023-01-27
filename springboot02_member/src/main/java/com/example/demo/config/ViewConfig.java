package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import com.example.demo.board.view.BoardDownLoadView;


@Configuration
@ComponentScan
public class ViewConfig {

	@Bean
	public BeanNameViewResolver viewResolver(){
		BeanNameViewResolver bnvResolver=new BeanNameViewResolver();
		bnvResolver.setOrder(1);
		return bnvResolver;
	}
	
	@Bean("download")
	public View boardDownLoadView() {
		return new BoardDownLoadView();
	}
}







