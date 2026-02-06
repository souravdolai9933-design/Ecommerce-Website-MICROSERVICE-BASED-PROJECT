package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PasswordResetToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String token;
	
   @OneToOne
   private Customer customer;
   
   private LocalDateTime expiryTime;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public Customer getCustomer() {
	return customer;
}

public void setCustomer(Customer customer) {
	this.customer = customer;
}

public LocalDateTime getExpiryTime() {
	return expiryTime;
}

public void setExpiryTime(LocalDateTime expiryTime) {
	this.expiryTime = expiryTime;
}

@Override
public String toString() {
	return "PasswordResetToken [id=" + id + ", token=" + token + ", customer=" + customer + ", expiryTime=" + expiryTime
			+ "]";
}
   
}
