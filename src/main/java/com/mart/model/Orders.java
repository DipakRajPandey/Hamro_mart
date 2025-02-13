package com.mart.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="orders")
@Data
public class Orders {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	@JsonIgnore
    private Users user;
	private String name;
	private String email;
	private String address;
	
	private String total_products;//for product name and quantity
	
	private double total_price;
	private String method;
	
	private String order_status="pending";
	               
	private String payment_status="pending";
	
	private String number;
	
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate date=LocalDate.now();
	@Override
	public String toString() {
	    return "Orders [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
	}
	
}
