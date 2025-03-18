package com.mart.apis;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mart.model.Products;
import com.mart.model.Users;
import com.mart.service.LoginService;
import com.mart.service.OrderService;
import com.mart.service.ProductService;
import com.mart.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/login")
public class LoginApi {
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
	public ResponseEntity<?> loginUser (@RequestParam("email") String email,
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
				 HttpHeaders hd=new HttpHeaders();
				 hd.add("admin", "/admin");
				return new ResponseEntity<>(hd,HttpStatus.OK) ;
			 
			 }
			 else {
				    List<Products> plist=ps.getAllProduct();
					model.addAttribute("pl",plist);
					HttpHeaders hd=new HttpHeaders();
					 hd.add("index", "/index");
					return new ResponseEntity<>(hd,HttpStatus.OK) ;
				   
			 }
		 }
		 model.addAttribute("user","password / email not match");
		 HttpHeaders hd=new HttpHeaders();
		 hd.add("login", "/login");
		return new ResponseEntity<>(hd,HttpStatus.OK) ;
	     }
	     else {
	    	 model.addAttribute("user","You are robot");
	    	 HttpHeaders hd=new HttpHeaders();
			 hd.add("login", "/login");
			return new ResponseEntity<>(hd,HttpStatus.OK) ;
	     }
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getDasboard (HttpSession session,Model model) {
	  
		   
	   if(((String) session.getAttribute("type")).equalsIgnoreCase("user")){
		   HttpHeaders hd=new HttpHeaders();
			 hd.add("index", "/index");
			return new ResponseEntity<>(hd,HttpStatus.OK) ;
		   
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
			 
			 HttpHeaders hd=new HttpHeaders();
			 hd.add("admin", "/admin");
			return new ResponseEntity<>(hd,HttpStatus.OK) ;
		   
	   }
	   
	   
	   HttpHeaders hd=new HttpHeaders();
		 hd.add("login", "/login");
		return new ResponseEntity<>(hd,HttpStatus.OK) ;
	   
	}
	//this is home page button 
@GetMapping("/admin")
  public ResponseEntity<?> getAdminPage(Model model,HttpSession session) {
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
	 HttpHeaders hd=new HttpHeaders();
	 hd.add("admin", "/admin");
	return new ResponseEntity<>(hd,HttpStatus.OK) ;
	
	}
	model.addAttribute("user","First login to site ");
	HttpHeaders hd=new HttpHeaders();
	 hd.add("login", "/login");
	return new ResponseEntity<>(hd,HttpStatus.OK) ;
	
}

@GetMapping("/logout")
public ResponseEntity<?> logOut(HttpSession session) {
	 
	session.invalidate();
	HttpHeaders hd=new HttpHeaders();
	 hd.add("login", "/login");
	return new ResponseEntity<>(hd,HttpStatus.OK) ;
}

//show userlist in adimin header
@GetMapping("/normaluser")
public ResponseEntity<?> showNormalUser(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	model.addAttribute("userlist",logser.getAllUser());
	HttpHeaders hd=new HttpHeaders();
	 hd.add("login", "/login");
	return new ResponseEntity<>(logser.getAllUser(),hd,HttpStatus.OK) ;
	
	}
	model.addAttribute("user","First login to site ");
	HttpHeaders hd=new HttpHeaders();
	 hd.add("login", "/admin_normal_user");
	return new ResponseEntity<>(hd,HttpStatus.OK) ;
}
@GetMapping("/showadmin")
public ResponseEntity<?>showAdminUser(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	model.addAttribute("userlist",logser.getAllAdmins());
	
	HttpHeaders hd=new HttpHeaders();
	 hd.add("admin_admin", "/admin_admin");
	return new ResponseEntity<>(logser.getAllAdmins(),hd,HttpStatus.OK) ;
	}
	model.addAttribute("user","First login to site ");
	HttpHeaders hd=new HttpHeaders();
	 hd.add("login", "/login");
	return new ResponseEntity<>(hd,HttpStatus.OK) ;
}


}
