package com.mart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mart.model.Carts;
import com.mart.model.Products;
import com.mart.service.CartsService;
import com.mart.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	private CartsService cs;
	@Autowired
	private ProductService ps;
	@GetMapping("/index")
	public String getIndext(Model model) {
		   List<Products> plist=ps.getAllProduct();
			model.addAttribute("pl",plist);
		return"index";
	}
@GetMapping("/addtocart")
public String getCart(Model model,HttpSession session) {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
	List<Carts> carts=cs.getAllCarts((int) session.getAttribute("id"));
	for(Carts c:carts) {
		System.out.println("------"+c.getName());
	}
	
	
	long count=carts.size();
	model.addAttribute("clist",carts);
	model.addAttribute("cnt",count);
	List<Object[]> obj=cs.totalPrice((int) session.getAttribute("id"));
	double sum=0;
	for (Object[] row : obj ) {
	    double price = (double) row[0];  // First column (price)
	    int quantity = (int) row[1];     // Second column (quantity)
	     sum+=price*quantity;
	}
	model.addAttribute("totalPrice",sum);
	return"user_cart";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
@PostMapping("/addcart")
public String addToCart(@ModelAttribute Carts carts,HttpSession session,Model model)  {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
      cs.addCart(carts);
	return"redirect:/addtocart";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}


///deletecart
@GetMapping("/deletecart/{id}")
public String deleteCart(@PathVariable("id") String id,HttpSession session,Model model) {
	if(session.getAttribute("id")!=null) {
	cs.deleteCart(Integer.parseInt(id));
	return"redirect:/addtocart";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
//update carts 
@PostMapping("/updatecart")
public String updateCart(@RequestParam("quantity") int quantity,
		@RequestParam("id")int id,HttpSession session,Model model){
	if(session.getAttribute("id")!=null) {
	Carts cart=cs.getCartId(id);
	cart.setQuantity(quantity);
	cs.updateCart(cart);
	return"redirect:/addtocart";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}


}
