package com.example.demo.Configuiration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class RazorpayConfig {
	 @Bean
	    public RazorpayClient razorpayClient(RazorpayProperties props)
	            throws RazorpayException {

	        return new RazorpayClient(
	                props.getId(),
	                props.getSecret()
	        );
	    }
	}

