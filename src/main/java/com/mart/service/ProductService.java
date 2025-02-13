package com.mart.service;

import java.util.List;

import com.mart.model.Products;

public interface ProductService {
 public long getProductCount();
 public Products addProduct(Products ps);
 public List<Products> getAllProduct();
 public void deleteProduct(int id);
 public Products getProductBYId(int id);
 public Products updateProduct(Products p);
}
