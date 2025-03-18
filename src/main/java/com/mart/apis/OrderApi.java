package com.mart.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mart.model.Orders;
import com.mart.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderApi {
	@Autowired
	private OrderService os;
 @GetMapping("/adminorder")
 public ResponseEntity<?>  getOrderDetails(Model model,HttpSession session) {
	 if(session.getAttribute("id")!=null) {
		 return new ResponseEntity<>( os.getAllOrders(),HttpStatus.OK);
	
	 }
	 model.addAttribute("user","First login to site ");
	 HttpHeaders header=new HttpHeaders();
	 header.add("login", "/login");
		return new ResponseEntity<>(header,HttpStatus.FOUND);
 }
	@PutMapping("/updateadminorder/{id}")
	public ResponseEntity<?> updateAdminOrder(@PathVariable("id") int id ,
			@RequestParam("payment_status") String payment_status,
			@RequestParam("order_status") String order_status,
			HttpSession session,Model model){
	  if(session.getAttribute("id")!=null) {
		Orders order=os.getOrderById(id);
		order.setPayment_status(payment_status);
		order.setOrder_status(order_status);
		
		os.updateOrder(order);
		
		HttpHeaders h=new HttpHeaders();
		
		h.add("updateorder", "/adminorder");
	return new ResponseEntity<>(h,HttpStatus.OK);
	  }
	  model.addAttribute("user","First login to site ");
	HttpHeaders h=new HttpHeaders();
		
		h.add("updateorder", "/login");
	return new ResponseEntity<>(h,HttpStatus.FOUND);	
	
	
	}
	@DeleteMapping("/deleteadminorder/{id}")
	public ResponseEntity<?> deleteAdminOrder(@PathVariable("id")String id,HttpSession session,
			Model model) {
		if(session.getAttribute("id")!=null) {
		os.deleteOrders(Integer.parseInt(id));
		HttpHeaders hd=new HttpHeaders();
		hd.add("adminorder","redirect:/adminorder");
		return  new ResponseEntity<>(hd,HttpStatus.OK);
		}
		model.addAttribute("user","First login to site ");
		HttpHeaders hd=new HttpHeaders();
		hd.add("adminorder","/login");
		return  new ResponseEntity<>(hd,HttpStatus.OK);

	}
@GetMapping("/userorder")
public ResponseEntity<?> getUserOrder(HttpSession session,Model model) {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
	List<Orders> order=os.getOrdersByUserId((int)(session.getAttribute("id")));
	if(order.size()!=0) {
	//System.out.println(session.getAttribute("id"));
	//System.out.println(order);
	model.addAttribute("olist",order);
	return new ResponseEntity<>(order,HttpStatus.OK);
	}else{
	model.addAttribute("msg","No order present");
	HttpHeaders hd=new HttpHeaders();
	hd.add("userorderlist","/userorderlist");
	return new ResponseEntity<>(hd,HttpStatus.NOT_FOUND);
	}
	}
	model.addAttribute("user","First login to site ");
	HttpHeaders hd=new HttpHeaders();
	hd.add("userorderlist","/login");
	return new ResponseEntity<>(hd,HttpStatus.FORBIDDEN);
	}
@GetMapping("/userorderlist")
public String getUserOrdersPage() {
	return "user_orders";
}

// Change Order status 
@PutMapping("/cancelorder/{id}")
public ResponseEntity<?> calcelOrder(@PathVariable("id") String id,
		HttpSession session,Model model) {
	
	if(session.getAttribute("id")!=null) {
		Orders order=os.getOrderById(Integer.parseInt(id));
	
	order.setId(Integer.parseInt(id));
	order.setOrder_status("cancelled");
	 os.addOrder(order);
	 
	 HttpHeaders hd=new HttpHeaders();
	 hd.add("cancleorder","/userorder");
	 
	return new ResponseEntity<>(hd,HttpStatus.OK);
	}model.addAttribute("user","First login to site ");
	HttpHeaders hd=new HttpHeaders();
	 hd.add("cancleorder","/login");
	 
	return new ResponseEntity<>(hd,HttpStatus.FOUND);
}
}
