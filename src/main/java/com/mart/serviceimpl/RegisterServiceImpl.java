package com.mart.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mart.model.Users;
import com.mart.repository.RegisterRepository;
import com.mart.service.RegisterService;
@Service
public class RegisterServiceImpl  implements RegisterService{
    @Autowired
	private RegisterRepository rr;
	
	@Override
	public Users registeUser(Users user) {
		// TODO Auto-generated method stub
		
		Users  isAlreadyExist= rr.findByEmail(user.getEmail());
		if(isAlreadyExist==null) {
			Users user1=rr.save(user);
			return user1;
		}
		
		return null;
	}

}
