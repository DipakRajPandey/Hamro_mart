package com.mart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mart.model.Users;
import com.mart.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService rs;
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String setRegister(@ModelAttribute Users user ,Model model) {
		     Users result=rs.registeUser(user);
		     if(result!=null) {
		    	 return"login";
		     }
		
		return"redirect:register";
	}
	
}
