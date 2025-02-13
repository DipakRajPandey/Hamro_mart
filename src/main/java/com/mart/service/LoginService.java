package com.mart.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.mart.model.Users;

public interface LoginService {
 public Users isLogin(String email,String password);
 
 public long getTotalAdmin(); 
 public long getTotalUser();
 public List<Users> getAllUser();
 public List<Users> getAllAdmins();
}
