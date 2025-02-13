package com.mart.service;
import java.util.ArrayList;
import java.util.List;

import com.mart.model.*;
public interface CartsService {
 public List<Carts> getAllCarts(int id);
 public List<Carts> getAllCarts();
 public void deleteCart(int id );
 public void updateCart(Carts cart);
 public void addCarts(Carts cart);
 public long getCount(int id );
 
 
 
 
 //add item
 public void addCart(Carts carts);
 public List<Object[]> totalPrice(int id);
public Carts getCartId(int id );
public void deleteCartItemByUserId(int id );

}
