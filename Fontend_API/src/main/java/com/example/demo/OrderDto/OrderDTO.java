package com.example.demo.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    
	private Long ordid;

    private String orderTrackingNumber;
    private Integer totalQuantity;
    private BigDecimal totalPrice;

    // Razor pay details
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String orderStatus;
    
    private Long rootUserId;
    
    private String deliveryName;
    private Long deliveryPhone;
    private String deliveryAddress;
    
     
    private List<OrderItemDTO> orderItems ;


	public Long getOrdid() {
		return ordid;
	}


	public void setOrdid(Long ordid) {
		this.ordid = ordid;
	}


	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}


	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}


	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}


	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}


	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}


	public String getRazorpaySignature() {
		return razorpaySignature;
	}


	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public Long getRootUserId() {
		return rootUserId;
	}


	public void setRootUserId(Long rootUserId) {
		this.rootUserId = rootUserId;
	}


	public String getDeliveryName() {
		return deliveryName;
	}


	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}


	public Long getDeliveryPhone() {
		return deliveryPhone;
	}


	public void setDeliveryPhone(Long deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}


	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}


	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}


	@Override
	public String toString() {
		return "OrderDTO [ordid=" + ordid + ", orderTrackingNumber=" + orderTrackingNumber + ", totalQuantity="
				+ totalQuantity + ", totalPrice=" + totalPrice + ", razorpayOrderId=" + razorpayOrderId
				+ ", razorpayPaymentId=" + razorpayPaymentId + ", razorpaySignature=" + razorpaySignature
				+ ", orderStatus=" + orderStatus + ", rootUserId=" + rootUserId + ", deliveryName=" + deliveryName
				+ ", deliveryPhone=" + deliveryPhone + ", deliveryAddress=" + deliveryAddress + "]";
	}
	 
}
