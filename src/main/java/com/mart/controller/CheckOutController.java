package com.mart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mart.model.Carts;
import com.mart.model.Orders;
import com.mart.service.CartsService;
import com.mart.service.OrderService;

import jakarta.servlet.http.HttpSession;
@Controller
public class CheckOutController {
	@Autowired
	private OrderService os;
	@Autowired
	private CartsService cs;
	//checkout 

	@GetMapping("/checkout")
	public String getCheckOut(Model model,HttpSession session) {
		 if(session.getAttribute("id")!=null) {
		  model.addAttribute("session",session);
		return"user_payment";
		 }model.addAttribute("user","First login to site ");
			return"login";
	}
	
	
	///ordercheckout
	@PostMapping("/ordercheckout")
	public String checkOut(@ModelAttribute Orders order,HttpSession session,Model model) {
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
		return"redirect:/userorder";
		}
		model.addAttribute("user","First login to site ");
		return"login";
	}

}
