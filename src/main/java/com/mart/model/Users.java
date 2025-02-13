package com.mart.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class Users {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
private String name;
@Column(unique=true)
private String email;
private String password;
private String user_type="user";

@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
@JsonManagedReference
private List<Carts> cartItems;

@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
@JsonManagedReference
private List<Orders> orders;

@Override
public String toString() {
    return "Users [id=" + id + ", name=" + name + ", email=" + email + ", user_type=" + user_type + "]";
}	
}
