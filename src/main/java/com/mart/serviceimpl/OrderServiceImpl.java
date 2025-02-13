package com.mart.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mart.model.Orders;
import com.mart.repository.OrderRepository;
import com.mart.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository or;
	
	
	@Override
	public long completeCount() {
		// TODO Auto-generated method stub
		try {
		return or.completeCount();
		}catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public long cancelOrder() {
		// TODO Auto-generated method stub
		try {
		return or.cancleOrder();
		}catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public long pendingCount() {
		// TODO Auto-generated method stub
		try {
		return or.pendingCount();
		}catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public long onTheWayCount() {
		// TODO Auto-generated method stub
		try {
		return or.onTheWayCount();
		}catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public double totalEarning() {
		// TODO Auto-generated method stub
		try {
		return or.totalEarning();
		}catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public double pendingEarning() {
		// TODO Auto-generated method stub
		try {
		return or.pendingEarning();
		}catch(Exception ex){
			return 0;
		}
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		List<Orders> os=or.findAll();
		return os;
	}

	@Override
	public Orders getOrderById(int id) {
		// TODO Auto-generated method stub
		try {
		return  or.findById(id).get();
		}catch(Exception ex) {
			return null;
		}
		
	}

	@Override
	public void updateOrder(Orders order) {
		// TODO Auto-generated method stub
		or.save(order);
	}

	@Override
	public void deleteOrders(int id) {
		// TODO Auto-generated method stub
		or.deleteById(id);
	}

	@Override
	public List<Orders> getOrdersByUserId(int id) {
		// TODO Auto-generated method stub
		try {
			return  or.getOrderByUserId(id);
			}catch(Exception ex) {
				return null;
			}
	}

	@Override
	public void addOrder(Orders order) {
		// TODO Auto-generated method stub
		or.save(order);
		
	}

}
