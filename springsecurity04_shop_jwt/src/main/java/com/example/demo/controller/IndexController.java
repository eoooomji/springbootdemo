package com.example.demo.controller;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.model.Role;
import com.example.demo.login.model.User;
import com.example.demo.login.repository.UserRepository;
import com.example.demo.security.service.PrincipalDetails;

//@RestController = @Controller + @ResponseBody

@RestController
public class IndexController {
	
	@Autowired
	public BCryptPasswordEncoder encodePassword;
	
	@Autowired
	public UserRepository userRepository;
	

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	//@Secured("ROLE_MEMBER")
	@GetMapping("/member/memberData")
	public  String memberData(Model model, Authentication authentication) {
		//Authentication 객체를 통해 유저 정보를 가져온다.
		PrincipalDetails userDetails= (PrincipalDetails) authentication.getPrincipal();
		System.out.printf("%s  %S\n", userDetails.getUsername(), userDetails.getPassword()  );
		Iterator<? extends GrantedAuthority> ite= userDetails.getAuthorities().iterator();
		while(ite.hasNext()) {			
			//System.out.println(ite.next().getAuthority());
			model.addAttribute("role", ite.next().getAuthority());
		}
		
		
     	model.addAttribute("userDetails", userDetails);
 		return "memberData";
	}
	
	//@Secured("ROLE_MANAGER")
	@GetMapping("/manager/managerData")
	public String managerData() {
		return "managerData";
	}
	
	//@Secured("ROLE_ADMIN")
	@GetMapping("/admin/adminData")
	public String adminData(){
		return "adminData";
	}
	
		
	@PostMapping("/join")
	public String join(@RequestBody User user) {
		System.out.println(user.getPassword());
		user.setPassword(encodePassword.encode(user.getPassword()));
		userRepository.saveUser(user);		
		return "회원가입완료";		
	}

}























