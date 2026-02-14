package com.example.demo.Repository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ShippingAddress;

public interface ShippingAddressRepo extends JpaRepository<ShippingAddress, Long>{
	
	
	ShippingAddress findByRootUserIdAndIsdefault(Long rootUserId, boolean isDefault);;
	
	public ShippingAddress findByAddid(Long id);
	
	public List<ShippingAddress> findByRootUserId(Long id);

}
