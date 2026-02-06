package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	

	@Autowired
	private JavaMailSender mailsender;
	
	public boolean sendEmail(String subject,String body, String to) {
		
		try {
			
			System.out.println("Controller Enter Email service..");
			
		MimeMessage minimemessage =	mailsender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(minimemessage);
		
		helper.setSubject(subject);
		helper.setText(body);
		helper.setTo(to);
		
		mailsender.send(minimemessage);
		System.out.println("Mail service :"+minimemessage);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Email send sucessfully..");
		return true;
	}
}
