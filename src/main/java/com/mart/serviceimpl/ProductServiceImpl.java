package com.mart.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mart.model.Products;
import com.mart.repository.ProductRepository;
import com.mart.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository ps;
	
	
	@Override
	public long getProductCount() {
		// TODO Auto-generated method stub
		try {
		return ps.count();
		}catch(Exception ex) {
			return 0;
		}
	}


	@Override
	public Products addProduct(Products p) {
		// TODO Auto-generated method stub
				
		return ps.save(p);
	}


	@Override
	public List<Products> getAllProduct() {
		// TODO Auto-generated method stub
		return ps.findAll();
	}


	@Override
	public void deleteProduct(int id) {
		// TODO Auto-generated method stub
		ps.deleteById(id);
		
	}


	@Override
	public Products getProductBYId(int id) {
		// TODO Auto-generated method stub
		return ps.getById(id);
	}


	@Override
	public Products updateProduct(Products p) {
		// TODO Auto-generated method stub
		return ps.save(p);
	}

}
