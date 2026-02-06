package com.example.demo.DTO;

public class ResetPasswordReq {
	
	private String token;
	private String newPassword;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "ResetPasswordReq [token=" + token + ", newPassword=" + newPassword + "]";
	}
	
	
}
