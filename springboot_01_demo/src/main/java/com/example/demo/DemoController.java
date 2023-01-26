package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// http://localhost:8090/

// http://localhost:8090/hello.do

@Controller
public class DemoController {
	
	@ResponseBody
	@RequestMapping("/")
	public String home() {
		System.out.println("Hello Boot!!!");
		return "Hello Boot!!!";
	}
	
	// src\main\webapp\WEB-INF\views 폴더 생성
	
	@RequestMapping("/hello.do")
	public String hello(Model model) {
		System.out.println("model");
		model.addAttribute("message", "hello");
		return "hello";
	}
}
