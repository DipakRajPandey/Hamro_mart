package com.mart.controller;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mart.model.Products;
import com.mart.model.Users;
import com.mart.service.LoginService;
import com.mart.service.OrderService;
import com.mart.service.ProductService;
import com.mart.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private LoginService logser;
	
	  @Autowired
		private ProductService ps;
	  @Autowired
	  private OrderService os;
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	@PostMapping("/login")
	public String loginUser (@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model,HttpSession session,
			@RequestParam("g-recaptcha-response") String grCode) throws IOException {
	          Users user=logser.isLogin(email, password);
	   	    
	     if(VerifyRecaptcha.verify(grCode)) {
		 if(user !=null) {
			 session.setAttribute("name",user.getName());
		     session.setAttribute("id", user.getId());
		     session.setAttribute("email", user.getEmail());
		     session.setAttribute("type", user.getUser_type());
		    // session.setMaxInactiveInterval(24*60*60);
		     session.setMaxInactiveInterval(24*60*60*60);
			 if(user.getUser_type().equalsIgnoreCase("admin")) {
				 
				 long productCount=ps.getProductCount();
				 model.addAttribute("pcount",productCount);
				 
				 System.out.println("pcount ="+productCount);
				 model.addAttribute("completorder",os.completeCount());
				 model.addAttribute("pendingorder",os.pendingCount());
				 model.addAttribute("onthewayorder",os.onTheWayCount());
				 model.addAttribute("cancelorder",os.cancelOrder());
				 model.addAttribute("totalorder",os.cancelOrder()+os.completeCount()+os.pendingCount()+os.onTheWayCount());
				 
				 model.addAttribute("totalearning",os.totalEarning());
				 model.addAttribute("pendingearning",os.pendingEarning());
				 
				 model.addAttribute("totaladmin",logser.getTotalAdmin());
				 
				 model.addAttribute("totaluser",logser.getTotalUser()); 
			 return"admin";
			 }
			 else {
				    List<Products> plist=ps.getAllProduct();
					model.addAttribute("pl",plist);
				    return"index";
			 }
		 }
		 model.addAttribute("user","password / email not match");
		return "login";
	     }
	     else {
	    	 model.addAttribute("user","You are robot");
	 		return "login";
	     }
	}
	
	@GetMapping("/")
	public String getDasboard (HttpSession session,Model model) {
	  
		   
	   if(((String) session.getAttribute("type")).equalsIgnoreCase("user")){
		   return"index";
	   }
	   
	   if(((String) session.getAttribute("type")).equalsIgnoreCase("admin")){
		   
			 long productCount=ps.getProductCount();
			 model.addAttribute("pcount",productCount);
			 
		//	 System.out.println("pcount ="+productCount);
			 model.addAttribute("completorder",os.completeCount());
			 model.addAttribute("pendingorder",os.pendingCount());
			 model.addAttribute("onthewayorder",os.onTheWayCount());
			 model.addAttribute("cancelorder",os.cancelOrder());
			 model.addAttribute("totalorder",os.cancelOrder()+os.completeCount()+os.pendingCount()+os.onTheWayCount());
			 
			 model.addAttribute("totalearning",os.totalEarning());
			 model.addAttribute("pendingearning",os.pendingEarning());
			 
			 model.addAttribute("totaladmin",logser.getTotalAdmin());
			 
			 model.addAttribute("totaluser",logser.getTotalUser()); 
			 
		   return"admin";
	   }
	   
	   
	   
	   return "redirect:login";
	}
	//this is home page button 
@GetMapping("/admin")
public String getAdminPage(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	long productCount=ps.getProductCount();
	 model.addAttribute("pcount",productCount);
	 
	 //System.out.println("pcount ="+productCount);
	 model.addAttribute("completorder",os.completeCount());
	 model.addAttribute("pendingorder",os.pendingCount());
	 model.addAttribute("onthewayorder",os.onTheWayCount());
	 model.addAttribute("cancelorder",os.cancelOrder());
	 model.addAttribute("totalorder",os.cancelOrder()+os.completeCount()+os.pendingCount()+os.onTheWayCount());
	 
	 model.addAttribute("totalearning",os.totalEarning());
	 model.addAttribute("pendingearning",os.pendingEarning());
	 
	 model.addAttribute("totaladmin",logser.getTotalAdmin());
	 
	 model.addAttribute("totaluser",logser.getTotalUser());
	return"admin";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}

@GetMapping("/logout")
public String logOut(HttpSession session) {
	 
	session.invalidate();
	return"redirect:login";
}

//show userlist in adimin header
@GetMapping("/normaluser")
public String showNormalUser(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	model.addAttribute("userlist",logser.getAllUser());
	return"admin_normal_user";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
@GetMapping("/showadmin")
public String showAdminUser(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	model.addAttribute("userlist",logser.getAllAdmins());
	return"admin_admin";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
}
