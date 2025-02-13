package com.mart.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mart.model.*;
public interface ProductRepository  extends JpaRepository<Products,Integer>{

}
