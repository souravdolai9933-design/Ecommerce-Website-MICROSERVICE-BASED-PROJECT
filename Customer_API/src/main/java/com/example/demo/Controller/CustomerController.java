package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ForgatPasswordRequestDTO;
import com.example.demo.DTO.RegistrationReq;
import com.example.demo.DTO.ResetPasswordReq;
import com.example.demo.DTO.UserLoginReqDTO;
import com.example.demo.Service.AuthService; 

@RestController
@RequestMapping("/auth")
public class CustomerController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> customerRegister(@RequestBody RegistrationReq r1) {
		
	    boolean b1= authService.Registration(r1);
	    
	     return ResponseEntity.ok("User Register");	
	}
	
	@PostMapping("/login")
	public String logIN(@RequestBody UserLoginReqDTO logDto) {
		
    String jwtToken = authService.login(logDto);
    System.out.println(jwtToken);
    
    return jwtToken;
	}
	
	@PostMapping("/forgatPass")
	public ResponseEntity<?> forgatPasswordReq(@RequestBody ForgatPasswordRequestDTO d2){
		
		authService.ForgotPasswordReq(d2);
		return ResponseEntity.ok("forgat password Req complete");
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassRequest(@RequestBody ResetPasswordReq r4){
		
		authService.resetPasswordReq(r4);
		
	return ResponseEntity.ok("Password Reset complete");
	 
		
	}
}
