package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.board.view.BoardDownLoadView;


@SpringBootApplication  // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Springboot02MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot02MemberApplication.class, args);
		
	}

}
