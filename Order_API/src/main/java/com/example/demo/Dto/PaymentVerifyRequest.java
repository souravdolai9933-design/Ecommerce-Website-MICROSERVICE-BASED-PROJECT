package com.example.demo.Dto;

public class PaymentVerifyRequest {
	
	private String razorPayPaymentId;
	private String razorPayOrderId;
	private String razorPaySignature;
	
	public String getRazorPayPaymentId() {
		return razorPayPaymentId;
	}
	public void setRazorPayPaymentId(String razorPayPaymentId) {
		this.razorPayPaymentId = razorPayPaymentId;
	}
	public String getRazorPayOrderId() {
		return razorPayOrderId;
	}
	public void setRazorPayOrderId(String razorPayOrderId) {
		this.razorPayOrderId = razorPayOrderId;
	}
	public String getRazorPaySignature() {
		return razorPaySignature;
	}
	public void setRazorPaySignature(String razorPaySignature) {
		this.razorPaySignature = razorPaySignature;
	}
}
