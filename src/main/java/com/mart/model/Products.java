package com.mart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="products")
@Data
public class Products {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id;
	private String name;
	private double price;
	private String image;
	private String category;
	
}
