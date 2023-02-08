package com.example.demo.login.model;

import java.sql.Timestamp;


public class User {
  private int id;
  private String username;
  private String password;
  private String email;
  private String authRole;
  private Timestamp createDate;
  
  public User() {
	
  }

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getAuthRole() {
	return authRole;
}

public void setAuthRole(String authRole) {
	this.authRole = authRole;
}

public Timestamp getCreateDate() {
	return createDate;
}

public void setCreateDate(Timestamp createDate) {
	this.createDate = createDate;
}
  
  
  
}
