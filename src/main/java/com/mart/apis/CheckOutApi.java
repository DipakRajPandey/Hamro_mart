package com.mart.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mart.model.Carts;
import com.mart.model.Orders;
import com.mart.service.CartsService;
import com.mart.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/check")
public class CheckOutApi {
	@Autowired
	private OrderService os;
	@Autowired
	private CartsService cs;
	//checkout 

	@GetMapping("/checkout")
	public ResponseEntity<?> getCheckOut(Model model,HttpSession session) {
		 if(session.getAttribute("id")!=null) {
		  model.addAttribute("session",session);
		  HttpHeaders hd=new HttpHeaders();
		  hd.add("userpayment","/user_payment");
		  
		return new ResponseEntity<>(hd,HttpStatus.OK);
		 }model.addAttribute("user","First login to site ");
		  HttpHeaders hd=new HttpHeaders();
		  hd.add("userpayment","/login");	
			return new ResponseEntity<>(hd,HttpStatus.OK);
	  
	}
	
	@GetMapping("/chec")
	public String  getCheck(Model model,HttpSession session) {
		 return  "user_payment";
	}
	
	
	
	
	///ordercheckout
	@PostMapping("/ordercheckout")
	public ResponseEntity<?> checkOut(@ModelAttribute Orders order,HttpSession session,Model model) {
		if(session.getAttribute("id")!=null) {  
		List<Carts>cart=cs.getAllCarts((int) session.getAttribute("id"));
		   StringBuffer str=new StringBuffer();
		  double total=0.0;
		  for(Carts cs:cart) {
			  double price= cs.getPrice();
			  int quantity=cs.getQuantity(); 
			  total+= (price*quantity); 
			  str.append(cs.getName()+"("+cs.getQuantity()+")");
		  }
		   order.setTotal_price(total);
		  order.setTotal_products(str.toString());
		   os.addOrder(order);
		 
		 cs.deleteCartItemByUserId((int)session.getAttribute("id"));
		return ResponseEntity.status(HttpStatus.CREATED).body("order placed");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("first login to the side");
	}

}
