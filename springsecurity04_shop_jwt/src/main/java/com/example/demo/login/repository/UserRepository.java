package com.example.demo.login.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.example.demo.login.model.User;

@Service
@Mapper  
public interface UserRepository {
   
	//회원가입
	void saveUser(User user);
	
	//로그인
	User getUserAccount(String username);
	
}
