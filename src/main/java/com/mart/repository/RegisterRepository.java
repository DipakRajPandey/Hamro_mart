package com.mart.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mart.model.*;
public interface RegisterRepository extends JpaRepository<Users,Integer> {
   public Users  findByEmail(String email);
  
}
