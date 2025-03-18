package com.mart.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mart.model.Carts;
import com.mart.model.Products;
import com.mart.service.CartsService;
import com.mart.service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartApi {
	@Autowired
	private CartsService cs;
	@Autowired
	private ProductService ps;
	@GetMapping("/index")
	public ResponseEntity<?> getIndext(Model model) {
		   List<Products> plist=ps.getAllProduct();
			model.addAttribute("pl",plist);
		return ResponseEntity.status(HttpStatus.OK).body(plist);
	}
@GetMapping("/addtocart")
public ResponseEntity<?> getCart(Model model,HttpSession session) {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
	List<Carts> carts=cs.getAllCarts((int) session.getAttribute("id"));
		
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
	return  ResponseEntity.status(HttpStatus.ACCEPTED).body(carts);
	}
	model.addAttribute("user","First login to site ");
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("First login to site");
}
@PostMapping("/addcart")
public ResponseEntity<?> addToCart(@ModelAttribute Carts carts,HttpSession session,Model model)  {
	if(session.getAttribute("type").toString().equalsIgnoreCase("user")) {
      cs.addCart(carts);
      return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart successfully.");
	}
	model.addAttribute("user","First login to site ");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("First login to the site.");

}


///deletecart
@DeleteMapping("/deletecart/{id}")
public ResponseEntity<?> deleteCart(@PathVariable("id") String id,HttpSession session,Model model) {
	if(session.getAttribute("id")!=null) {
	cs.deleteCart(Integer.parseInt(id));
    return ResponseEntity.status(HttpStatus.CREATED).body("Item deleted from cart successfully.");
	}
	model.addAttribute("user","First login to site ");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("First login to the site.");
}
//update carts 
@PostMapping("/updatecart")
public ResponseEntity<?> updateCart(@RequestParam("quantity") int quantity,
		@RequestParam("id")int id,HttpSession session,Model model){
	if(session.getAttribute("id")!=null) {
	Carts cart=cs.getCartId(id);
	cart.setQuantity(quantity);
	cs.updateCart(cart);
    return ResponseEntity.status(HttpStatus.CREATED).body("Item updated  to cart successfully.");
	}
	model.addAttribute("user","First login to site ");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("First login to the site.");

}

}
