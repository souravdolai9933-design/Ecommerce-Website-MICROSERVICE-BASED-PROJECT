package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.DTO.ForgatPasswordRequestDTO;
import com.example.demo.DTO.RegistrationReq;
import com.example.demo.DTO.ResetPasswordReq;
import com.example.demo.DTO.UserLoginReqDTO;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.PasswordResetToken;
import com.example.demo.Exception.CustomerNotFoundException;
import com.example.demo.Exception.EmailAlreadyExistsException;
import com.example.demo.Exception.IncorrectPasswordException;
import com.example.demo.Exception.InvalidCredentialsException;
import com.example.demo.Exception.PhoneNoAlreadyExists;
import com.example.demo.Exception.TokenExpiredException;
import com.example.demo.Exception.TokenNotFoundException;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.PasswordResetTokenRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
    
    private Long rootUserId = null;

    
    public String Registration(RegistrationReq regDto) {

        // Email duplicate check
        if (repo.findByEmail(regDto.getEmail()).isPresent()) {
            return "Email Alreadyexists";
        }

        // Phone duplicate check
        if (repo.findByPhoneno(regDto.getPhoneno()).isPresent()) {
        	
        	System.out.println("I am hit phn if Cond");
        	
            return "Phone no Already exists";
        }

        Customer customer = new Customer();
        customer.setName(regDto.getName());
        customer.setEmail(regDto.getEmail());
        customer.setPhoneno(regDto.getPhoneno());
        customer.setPassword(encoder.encode(regDto.getPassword()));

        repo.save(customer);

        emailService.sendEmail(
            "Your Account is Created",
            "Dear " + regDto.getName() + ",\n\n🎉 Registration Successful!\n\nBest regards,\nSourav",
            regDto.getEmail()
        );

        return "Registration Sucess";
    }

    
    public String login(UserLoginReqDTO logDto) {
    	System.out.println("Login Methode HIt//");

        Customer user = repo.findByEmail(logDto.getEmail())
                .orElseThrow(() -> new CustomerNotFoundException("User Not Found"));

        if (!encoder.matches(logDto.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect Password");
        }
        
        rootUserId = user.getId();
        
        System.out.println("Token Generate Sucessfully...");

        // ✅ Only return JWT
        return util.generateToken(user.getEmail());
    }

    
    public void ForgotPasswordReq(ForgatPasswordRequestDTO req) {

        Customer customer = repo.findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with email: " + req.getEmail())
                );

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setCustomer(customer);
        resetToken.setToken(token);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));

        passResetRepo.save(resetToken);

        emailService.sendEmail(
                "Reset Password",
                "Click to reset: http://localhost:3000/reset-password?token=" + token,
                customer.getEmail()
        );
    }

    
    public void resetPasswordReq(ResetPasswordReq resetPassReq) {

        PasswordResetToken passReset = passResetRepo.findByToken(resetPassReq.getToken())
                .orElseThrow(() ->
                        new TokenNotFoundException("Reset token not found")
                );

        if (passReset.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Reset token has expired");
        }

        Customer customer = passReset.getCustomer();
        customer.setPassword(encoder.encode(resetPassReq.getNewPassword()));
        repo.save(customer);

        passResetRepo.delete(passReset);

        emailService.sendEmail(
                customer.getEmail(),
                "Password Reset Successful",
                "Your password has been reset successfully."
        );
    }

     
    public Long getRootCustomerId( ) {
    	if(rootUserId == null ) throw new UsernameNotFoundException("User Not Found");
    	System.out.println("root user id :"+rootUserId);
   
        return rootUserId;
    }
}
