package com.mart.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mart.model.*;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Carts,Integer> {
	 @Query("SELECT COUNT(o) FROM Carts o WHERE o.user.id = :id")
	    
		public long getCountById(int id);
	 
	 @Query(" FROM Carts o WHERE o.user.id = :id")
		public List<Carts> getCarts(int id);
	 
	 @Query("SELECT o.price,o.quantity FROM Carts o WHERE o.user.id = :id")
		public List<Object[]> totalPrice(@Param("id") int id);
		  @Modifying
		  @Transactional
		@Query("Delete  FROM Carts o WHERE o.user.id = :id")
		public void deleteBYUserid(@Param("id") int id);
		
}
