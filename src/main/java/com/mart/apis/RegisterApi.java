package com.mart.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mart.model.Users;
import com.mart.service.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterApi {
	@Autowired
	private RegisterService rs;
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> setRegister(@ModelAttribute Users user ,Model model) {
		    
		try{
			Users result=rs.registeUser(user);
		     
		    	 return new ResponseEntity<>("register successfuly",HttpStatus.OK);
		     
		}catch(Exception ex) {
	    	 return new ResponseEntity<>("registation faild",HttpStatus.OK);

		}
		
		
	}
}
