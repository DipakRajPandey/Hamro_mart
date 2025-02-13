package com.mart.model;

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
@Table(name="cart")
@Data
public class Carts {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int id;
 
 @ManyToOne
 @JoinColumn(name = "user_id", nullable = false)
 @JsonIgnore
 @JsonBackReference
 private Users user;
 private String name;
 private String image;
 private double price;
 private int quantity;
 @Override
 public String toString() {
     return "Carts [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
 }
 
}
