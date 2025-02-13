package com.mart.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mart.model.Users;
import com.mart.repository.LoginRepository;
import com.mart.service.LoginService;
@Service
public class LoginServiceImpl  implements LoginService{
@Autowired
private LoginRepository logrep;
	@Override
	public Users isLogin(String email, String password) {
		try {
		Users usr=logrep.findByEmailAndPassword(email, password);
		return usr;
		}catch(Exception ex) {
			
			return null;
		}
	}
	@Override
	public long getTotalAdmin() {
		// TODO Auto-generated method stub
		return logrep.getTotalAdmin();
	}
	@Override
	public long getTotalUser() {
		// TODO Auto-generated method stub
		return logrep.getTotalUser();
	}
	@Override
	public List<Users> getAllUser() {
		// TODO Auto-generated method stub
		List<Users>user=logrep.getAllUser();
		return  user;
	}
	@Override
	public List<Users> getAllAdmins() {
		// TODO Auto-generated method stub
		List<Users>user=logrep.getAllAdmin();
		return  user;
	}

}
