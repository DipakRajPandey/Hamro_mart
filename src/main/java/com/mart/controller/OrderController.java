package com.mart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mart.model.Orders;
import com.mart.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService os;
 @GetMapping("/adminorder")
 public String getOrderDetails(Model model,HttpSession session) {
	 if(session.getAttribute("id")!=null) {
	 model.addAttribute("order",os.getAllOrders());
	 return"admin_order";
	 }
	 model.addAttribute("user","First login to site ");
		return"login";
 }
	@PostMapping("/updateadminorder")
	public String updateAdminOrder(@RequestParam("id") int id ,
			@RequestParam("payment_status") String payment_status,
			@RequestParam("order_status") String order_status,
			HttpSession session,Model model){
	  if(session.getAttribute("id")!=null) {
		Orders order=os.getOrderById(id);
		order.setPayment_status(payment_status);
		order.setOrder_status(order_status);
		
		os.updateOrder(order);
		
	return"redirect:adminorder";
	  }
	  model.addAttribute("user","First login to site ");
		return"login";
	}
	@GetMapping("/deleteadminorder/{id}")
	public String deleteAdminOrder(@PathVariable("id")String id,HttpSession session,
			Model model) {
		if(session.getAttribute("id")!=null) {
		os.deleteOrders(Integer.parseInt(id));
		
		return"redirect:/adminorder";
		}
		model.addAttribute("user","First login to site ");
		return"login";
	}
@GetMapping("/userorder")
public String getUserOrder(HttpSession session,Model model) {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
	List<Orders> order=os.getOrdersByUserId((int)(session.getAttribute("id")));
	if(order!=null) {
	//System.out.println(session.getAttribute("id"));
	//System.out.println(order);
	model.addAttribute("olist",order);
	return"user_orders";
	}
	model.addAttribute("msg","No order present");
	return"user_orders";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}

// Change Order status 
@GetMapping("/cancelorder/{id}")
public String calcelOrder(@PathVariable("id") String id,
		HttpSession session,Model model) {
	if(session.getAttribute("id")!=null) {
	Orders order=os.getOrderById(Integer.parseInt(id));
	
	order.setId(Integer.parseInt(id));
	order.setOrder_status("cancelled");
	 os.addOrder(order);
	return"redirect:/userorder";
	}model.addAttribute("user","First login to site ");
	return"login";
}





}
