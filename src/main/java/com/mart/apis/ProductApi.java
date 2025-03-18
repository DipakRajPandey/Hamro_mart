package com.mart.apis;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mart.model.Products;
import com.mart.service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/product")
public class ProductApi {
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
  public ResponseEntity<String> setProduct(@RequestParam("name") String name,
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
		   //image.transferTo(filePath);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return new ResponseEntity<>("product added",HttpStatus.OK);
	  }
	  //model.addAttribute("user","First login to site ");
	  return new ResponseEntity<>("First login to site",HttpStatus.OK);
  }
  
@GetMapping("/adminproduct")
public ResponseEntity<List<Products>> getAdminProduct(Model model,HttpSession session) {

		try {
	List<Products> plist=ps.getAllProduct();
	model.addAttribute("pl",plist);
	return new ResponseEntity<>(plist,HttpStatus.OK);
	}catch(Exception ex) {
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
}
@GetMapping("/delete/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable("id") String id,HttpSession session,Model model) {
	//System.out.println("id is "+id);
	if(session.getAttribute("id")!=null) {
	 ps.deleteProduct(Integer.parseInt(id));
	return new ResponseEntity<>("product deleted",HttpStatus.OK);
	}
	return  new ResponseEntity<>("First login to site ",HttpStatus.OK);

}


// update products
@GetMapping("/product/update/{id}")
public ResponseEntity<Products> updateProduct(@PathVariable("id") int id,Model model,HttpSession session) {
	
	try{ 
			Products p=ps.getProductBYId(id);
			
		model.addAttribute("user","First login to site ");
		return new ResponseEntity<>(p,HttpStatus.OK);

	}catch(Exception ex) {
		return new ResponseEntity<>(null,HttpStatus.OK);

	}
	
}
@PostMapping("/updateproduct")
public ResponseEntity<String> updateProduct( @RequestParam("name") String name,
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
return new ResponseEntity<>("product updated",HttpStatus.OK);
}
	model.addAttribute("user","First login to site ");
	return new ResponseEntity<>("First login to site",HttpStatus.NOT_FOUND);
}
}
