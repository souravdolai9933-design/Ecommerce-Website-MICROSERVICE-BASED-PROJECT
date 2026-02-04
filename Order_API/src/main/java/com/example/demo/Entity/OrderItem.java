package com.example.demo.Entity;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Override
	public String toString() {
		return "OrderItem [id=" + id + ", productId=" + productId + ", productName=" + productName + ", price=" + price
				+ ", quantity=" + quantity  + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;      // from Product Service
    private String productName;
    private BigDecimal price;
    private int quantity;

    // 🔗 Many Items → One Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
    
}
	
	



