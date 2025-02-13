package com.mart.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mart.model.*;
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.order_status = 'delivered'")
    
	public long completeCount();
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.order_status = 'cancelled'")
	public long cancleOrder();
   
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.order_status = 'pending'")
	public long pendingCount();
  
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.order_status = 'on the way'")
	public long onTheWayCount();
	
	
	@Query("Select Sum(o.total_price) from Orders o where o.payment_status = 'paid'" )
	public double totalEarning();
	
	@Query("Select Sum(o.total_price) from Orders o where o.payment_status = 'pending'" )
	public double pendingEarning();
	
	@Query(" FROM Orders o WHERE o.user.id = :id")
	public List<Orders> getOrderByUserId(@Param("id") int id);
}
