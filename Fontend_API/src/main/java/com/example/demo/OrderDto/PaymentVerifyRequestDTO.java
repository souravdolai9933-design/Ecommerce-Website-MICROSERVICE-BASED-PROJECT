package com.example.demo.OrderDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentVerifyRequestDTO {
	
	  @JsonProperty("razorpay_payment_id")
	    private String razorPayPaymentId;

	    @JsonProperty("razorpay_order_id")
	    private String razorPayOrderId;

	    @JsonProperty("razorpay_signature")
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
	@Override
	public String toString() {
		return "PaymentVerifyRequestDTO [razorPayPaymentId=" + razorPayPaymentId + ", razorPayOrderId="
				+ razorPayOrderId + ", razorPaySignature=" + razorPaySignature + "]";
	}

}
