package com.example.demo.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.Entity.*;
import com.example.demo.Service.OrderServiceClass;
import com.razorpay.RazorpayException;
import com.example.demo.Dto.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderServiceClass orderService; 
	
	@PostMapping("/create")
	public Order CreateOrder(@RequestBody CheckoutRequest check) throws RazorpayException {
		
		System.out.println("I am hit the Create Order controller...");
		
		 return orderService.createOrder(check);
			
	}
	@PostMapping("/payment-verify")
	 public Order verifyPayment(
	            @RequestBody PaymentVerifyRequest paymentVerifyRequest) throws RazorpayException {

	        return orderService.verifyRequest(paymentVerifyRequest);
	         
	    }
	
	@GetMapping("/default-Address/{rootUserId}")
	public ShippingAddress getDefaultShippingAddress(@PathVariable Long rootUserId) {
		
		return orderService.getShippingAddress(rootUserId);
		
	}
	
	@GetMapping("/get-Address/{rootUserId}")
	public List<ShippingAddress> getShippingAdress(@PathVariable Long rootUserId){
		
		return orderService.getAllExistsShippingAddress(rootUserId);
	}
	
	
	@GetMapping("/getAddress/{id}")
	public ShippingAddress getShippingAddress(@PathVariable Long id) {
		
		System.out.println("I am inside getShippingAddress");
		
		System.out.println("id come from frontend : ->"+id);
		return orderService.getShippingAddressById(id);
		
	}
	
	@PutMapping("/update-Address")
	public String editShippingAddress(@RequestBody ShippingAddressDTO d4) {
		return orderService.updateShippingAddress(d4);
	}
	
	@DeleteMapping("/delete-Address/{id}")
	public String deleteShippingAddress(@PathVariable Long id) {
		
		return orderService.deleteShippingAddress(id);
		
	}
	
	@PutMapping("/save-Address")
	public ShippingAddress saveShippingAddress(@RequestBody ShippingAddressDTO d3) {
		return orderService.saveShippingAddress(d3);
		
	}
	
	@GetMapping("/root-id")
	public Long getRootUserId(@RequestParam Long id) {
		System.out.println("Root User Id :->>"+id);
		
		 orderService.changeAddressStatus(id);
		
	    return orderService.getRootUserId(id);
	}
	
	@GetMapping("/order-list")
	public List<Order> GetAllOrder(@RequestParam Long rootUserId){
		
		return orderService.getAllOrder(rootUserId);
	}

	
	@GetMapping("/call")
	public String getMessage() {
		return "order api called";
	}
	
	 
}
