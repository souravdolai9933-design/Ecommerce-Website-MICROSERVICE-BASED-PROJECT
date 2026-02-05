package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	 Optional<Customer> findByEmail(String email);
	
	

}
