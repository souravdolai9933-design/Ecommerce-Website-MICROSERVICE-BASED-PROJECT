package com.example.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configuration.SecurityConfig;
import com.example.demo.DTO.RegistrationReq;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.UserLoginReqDTO;
import com.example.demo.Repository.CustomerRepository;

@Service
public class AuthService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private SecurityConfig config;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private CustomerRepository repo;
	 
	@Autowired
    private EmailService emailService;
	    
	  public boolean Registration(RegistrationReq RegDto) {
		  
		  Customer c1 =  new Customer();
		  
		  c1.setName(RegDto.getName());
		  c1.setPassword(encoder.encode(RegDto.getPassword()));
		  c1.setPhoneno(RegDto.getPhoneno());
		  c1.setEmail(RegDto.getEmail());
		   Customer saveCustomer = repo.save(c1);
		  
		  if(null != saveCustomer) {
				 
				 String subject ="Your Account is Created";
				 
				 String body =
					        "Dear " + RegDto.getName() + ",\n\n"
					      + "🎉 Registration Successful!\n\n"
					      + "Your account has been created successfully. You can now log in and start using our services.\n\n"
					      + "If you need any help, feel free to contact our support team.\n\n"
					      + "Best regards,\n"
					      + "Sourav";


				 String to = RegDto.getEmail();
				 
				 
				 emailService.sendEmail(subject, body, to); 
				 return true; 
			 }
			return false;
	  }
	  
	  public String login(UserLoginReqDTO logDto) {
		  
		  Customer u3 = repo.findByEmail(logDto.getEmail())
				  .orElseThrow(()->new RuntimeException("User Not found"));
		  

	        if (!encoder.matches(logDto.getPassword(), u3.getPassword())) {
	            throw new RuntimeException("Invalid credentials");
	        }
	        return util.generateToken(u3.getEmail());
	  }
	  
	  
	

}
