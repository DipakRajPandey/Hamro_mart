package com.mart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mart.model.Products;
import com.mart.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService ps;
	
  @GetMapping("/addproduct")
  public String getAddProduct(HttpSession session,Model model) {
	  if(session.getAttribute("id")!=null) {
	  return"admin_add_product";
	  }model.addAttribute("user","First login to site ");
		return"login";
  }
  
  @PostMapping("/addproduct")
  public String setProduct(@RequestParam("name") String name,
		  HttpSession session,Model model,
		  @RequestParam("price")double price,
		  @RequestParam("category") String category,
		  
		  @RequestParam("image") MultipartFile image) {
	  if(session.getAttribute("id")!=null) {
	  String fileName = image.getOriginalFilename();
	  Products product =new Products();
	  product.setCategory(category);
	  product.setName(name);
	  product.setPrice(price);
	  product.setImage(fileName);
	  Products p=ps.addProduct(product);
	  
	  try {
		  Path uploadPath = Path.of("src/main/resources/static/uploaded_img/"+fileName);
		   Path filePath = uploadPath.resolve(fileName);
		//Files.copy(image.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
		   image.transferTo(uploadPath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return"redirect:adminproduct";
	  }
	  model.addAttribute("user","First login to site ");
		return"login";
  }
  
@GetMapping("/adminproduct")
public String getAdminProduct(Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) {
	List<Products> plist=ps.getAllProduct();
	model.addAttribute("pl",plist);
	return"admin_products";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
@GetMapping("/delete/{id}")
public String deleteProduct(@PathVariable("id") String id,HttpSession session,Model model) {
	//System.out.println("id is "+id);
	if(session.getAttribute("id")!=null) {
	 ps.deleteProduct(Integer.parseInt(id));
	return"redirect:/adminproduct";
	}model.addAttribute("user","First login to site ");
	return"login";
}


// update products
@GetMapping("/product/update/{id}")
public String updateProduct(@PathVariable("id") int id,Model model,HttpSession session) {
	if(session.getAttribute("id")!=null) { 
	model.addAttribute("product",ps.getProductBYId(id));
	return"UpdateProduct";
	}
	model.addAttribute("user","First login to site ");
	return"login";
}
@PostMapping("/updateproduct")
public String updateProduct( @RequestParam("name") String name,
HttpSession session ,Model model,
@RequestParam("price")double price,
@RequestParam("category") String category,

@RequestParam("image") MultipartFile image,
@RequestParam("id") int id) {
	
	if(session.getAttribute("id")!=null) {
String fileName = image.getOriginalFilename();
Products product =new Products();
product.setCategory(category);
product.setName(name);
product.setPrice(price);
product.setImage(fileName);
product.setId(id);
Products p=ps.updateProduct(product);

try {
Path uploadPath = Path.of("src/main/resources/static/uploaded_img/"+fileName);
 Path filePath = uploadPath.resolve(fileName);
//Files.copy(image.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
 image.transferTo(uploadPath);
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return"redirect:adminproduct";
}
	model.addAttribute("user","First login to site ");
	return"login";
}
}
