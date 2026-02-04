package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		
		System.out.println("I am hit the controller...");
		
		 return orderService.createOrder(check);
			
	}
	@PostMapping("/payment-verify")
	 public Order verifyPayment(
	            @RequestBody PaymentVerifyRequest paymentVerifyRequest) throws RazorpayException {

	        return orderService.verifyRequest(paymentVerifyRequest);
	         
	    }
	
	@GetMapping("/call")
	public String getMessage() {
		return "order api called";
	}
}
