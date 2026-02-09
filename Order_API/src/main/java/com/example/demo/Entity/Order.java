package com.example.demo.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class) // ✅ REQUIRED
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems ;

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

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Order [ordid=" + ordid + ", orderTrackingNumber=" + orderTrackingNumber + ", totalQuantity="
				+ totalQuantity + ", totalPrice=" + totalPrice + ", razorpayOrderId=" + razorpayOrderId
				+ ", razorpayPaymentId=" + razorpayPaymentId + ", razorpaySignature=" + razorpaySignature
				+ ", orderStatus=" + orderStatus + ", rootUserId=" + rootUserId + ", deliveryName=" + deliveryName
				+ ", deliveryPhone=" + deliveryPhone + ", deliveryAddress=" + deliveryAddress + "]";
	}
	
 }
