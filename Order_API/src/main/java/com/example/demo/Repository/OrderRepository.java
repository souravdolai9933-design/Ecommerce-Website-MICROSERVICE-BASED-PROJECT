package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	
	Optional<Order> findByRazorpayOrderId(String orderid);

}
