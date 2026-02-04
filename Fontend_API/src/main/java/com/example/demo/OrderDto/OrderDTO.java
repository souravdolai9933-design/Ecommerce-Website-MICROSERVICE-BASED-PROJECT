package com.example.demo.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

 

public class OrderDTO {

    
    private Long ordid;

    private String orderTrackingNumber;
    private Integer totalQuantity;
    private BigDecimal totalPrice;

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String orderStatus;

    
    private LocalDateTime orderCreated;

    
    private LocalDateTime orderUpdated;

    // 🔗 Many Orders → One Customer
   
    private CustomerDTO customer;

    // 🔗 One Order → Many OrderItems
    
    private List<OrderItemDTO> orderItems;

    // 🔗 One Order → One Address
    
    private ShippingAddressDTO shippingAddress;

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

	public LocalDateTime getOrderCreated() {
		return orderCreated;
	}

	public void setOrderCreated(LocalDateTime orderCreated) {
		this.orderCreated = orderCreated;
	}

	public LocalDateTime getOrderUpdated() {
		return orderUpdated;
	}

	public void setOrderUpdated(LocalDateTime orderUpdated) {
		this.orderUpdated = orderUpdated;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public ShippingAddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public String toString() {
		return "OrderDTO [ordid=" + ordid + ", orderTrackingNumber=" + orderTrackingNumber + ", totalQuantity="
				+ totalQuantity + ", totalPrice=" + totalPrice + ", razorpayOrderId=" + razorpayOrderId
				+ ", razorpayPaymentId=" + razorpayPaymentId + ", razorpaySignature=" + razorpaySignature
				+ ", orderStatus=" + orderStatus + ", orderCreated=" + orderCreated + ", orderUpdated=" + orderUpdated
				+  "]";
	}
}
