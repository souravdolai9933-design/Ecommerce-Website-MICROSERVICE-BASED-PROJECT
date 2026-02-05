package com.example.demo.Service;

import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	public final String serect ="Sourav_Ecom_WEB_993311";
	
	@SuppressWarnings("deprecation")
	public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(0))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, serect)
                .compact();
    }
	
	
	public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(serect)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	

}
