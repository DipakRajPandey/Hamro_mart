package com.mart.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mart.model.Carts;
import com.mart.repository.CartRepository;
import com.mart.service.CartsService;
@Service
public class CartsServiceImpl implements CartsService {

	 @Autowired
	 private CartRepository  cs;
	@Override
	public List<Carts> getAllCarts(int id  ) {
		// TODO Auto-generated method stub
		
		List<Carts> cart=cs.getCarts(id);
		return cart;
	}

	@Override
	public void deleteCart(int id) {
		// TODO Auto-generated method stub
		cs.deleteById(id);
	}

	@Override
	public void updateCart(Carts cart) {
		// TODO Auto-generated method stub
		   cs.save(cart);
	}

	@Override
	public void addCarts(Carts cart) {
		// TODO Auto-generated method stub
		cs.save(cart);
	}

	@Override
	public long getCount(int id) {
		// TODO Auto-generated method stub
		return cs.getCountById(id);
	}

	@Override
	public List<Carts> getAllCarts() {
		// TODO Auto-generated method stub
		List<Carts> cart=cs.findAll();
		return cart;
	}

	@Override
	public void addCart(Carts carts) {
		// TODO Auto-generated method stub
		cs.save( carts);
	}

	@Override
	public List<Object[]> totalPrice(int id) {
	    try {
	        List<Object[]> obj = cs.totalPrice(id);
	        return obj;
	    } catch (DataAccessException ex) { // Catch specific exceptions
	        System.err.println("Error fetching total price: " + ex.getMessage());
	        return Collections.singletonList(new Object[]{0, 0}); // Corrected List initialization
	    }
	
	}

	@Override
	public Carts getCartId(int id) {
		// TODO Auto-generated method stub
		return cs.findById(id).get();
	}

	@Override
	public void deleteCartItemByUserId(int id) {
		// TODO Auto-generated method stub
		cs.deleteBYUserid(id);
	}

}
