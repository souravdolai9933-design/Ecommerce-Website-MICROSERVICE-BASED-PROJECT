package com.example.demo.Service;

 

import java.time.LocalDateTime;
 
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

 
import com.example.demo.DTO.ForgatPasswordRequestDTO;
import com.example.demo.DTO.RegistrationReq;
import com.example.demo.DTO.ResetPasswordReq;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.PasswordResetToken;
 import com.example.demo.DTO.*;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.PasswordResetTokenRepo;

@Service
public class AuthService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private CustomerRepository repo;
	 
	@Autowired
    private EmailService emailService;
	
	@Autowired
	private PasswordResetTokenRepo passResetRepo;
	    
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
		  
		  System.out.println("Request Come to login method...");
		  
		  Customer u3 = repo.findByEmail(logDto.getEmail())
				  .orElseThrow(()->new RuntimeException("User Not found"));
		  
		  System.out.println("User Found Sucessfully...");

	        if (!encoder.matches(logDto.getPassword(), u3.getPassword())) {
	            throw new RuntimeException("Invalid credentials");
	        }
	        
	        System.out.println("Generate Token Sucessfully..");
	        
	        return util.generateToken(u3.getEmail());
	  }
	  
	  public void ForgotPasswordReq(ForgatPasswordRequestDTO req) {
		  System.out.println("Enter forgot password methode...");
		  
		  Customer c2 = repo.findByEmail(req.getEmail())
				  .orElseThrow(()->new RuntimeException("Customer not found"));
		  
		  String token = UUID.randomUUID().toString();
		  
		  PasswordResetToken Passreset = new PasswordResetToken();
		  Passreset.setCustomer(c2);
		  Passreset.setToken(token);
		  Passreset.setExpiryTime(LocalDateTime.now().plusMinutes(10));
		  
		  System.out.println("Before save Data to backend "+Passreset);
		  
		  passResetRepo.save(Passreset);
		  
		  emailService.sendEmail(
				    "Reset Password",   // subject
				    "Click to reset: http://localhost:3000/reset-password?token=" + token, // body
				    c2.getEmail()       // to
				);  
	  }
	  
	  public void resetPasswordReq(ResetPasswordReq resetPassReq) {
		  
		  // Token Found
		  PasswordResetToken passReset = passResetRepo.findByToken(resetPassReq.getToken())
				  .orElseThrow(()->new RuntimeException("Token Not Found")) ;
		  
		  // Token Time Validate
		  
		 if(passReset.getExpiryTime().isBefore(LocalDateTime.now())){
			 throw new RuntimeException("Token Expire");
		 } 
		 
		 Customer customer = passReset.getCustomer();
		 customer.setPassword(encoder.encode(resetPassReq.getNewPassword()));
		 repo.save(customer);
		 
		 passResetRepo.delete(passReset); 
		 
		    String emailBody =
		            "Hello " + customer.getName() + ",\n\n" +
		            "We’re writing to confirm that your account password has been successfully reset.\n\n" +
		            "You can now log in using your new password.\n\n" +
		            "If you did not request this change, please contact our support team immediately to secure your account.\n\n" +
		            "For your security:\n" +
		            "* Never share your password with anyone\n" +
		            "* Use a strong and unique password\n" +
		            "* Avoid logging in from public or shared devices\n\n" +
		            "Thank you for using our service.\n\n" +
		            "Best regards,\n" +
		            "Sourav E-Commerce Support Team";

		    // 6️⃣ Send success email
		    emailService.sendEmail(
		            customer.getEmail(),
		            "Your Password Has Been Reset Successfully",
		            emailBody
		    );
	  }
}
