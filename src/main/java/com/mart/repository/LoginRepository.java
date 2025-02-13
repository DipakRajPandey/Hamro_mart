package com.mart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mart.model.*;
public interface LoginRepository  extends JpaRepository<Users,Integer>{
public Users findByEmailAndPassword(String email , String password);
	


///user methods 
@Query("SELECT COUNT(o) FROM Users o WHERE o.user_type = 'admin'")
public long getTotalAdmin();
@Query("SELECT COUNT(o) FROM Users o WHERE o.user_type = 'user'")
public long getTotalUser();

@Query("  FROM Users o WHERE o.user_type = 'user'")
public List<Users> getAllUser();


@Query("FROM Users o WHERE o.user_type = 'admin'")
public List<Users> getAllAdmin();
}
