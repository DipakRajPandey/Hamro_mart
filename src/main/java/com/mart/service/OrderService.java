package com.mart.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.mart.model.Orders;

public interface OrderService {
	public long completeCount();
 	public long cancelOrder(); 
	public long pendingCount();
	public long onTheWayCount();
	public double totalEarning();
	public double pendingEarning();
	
	public List<Orders> getAllOrders();
	
	public Orders getOrderById(int id );
	public List<Orders> getOrdersByUserId(int id);
	public void updateOrder(Orders order);
	public void deleteOrders(int id);
	public void addOrder(Orders order);
}
