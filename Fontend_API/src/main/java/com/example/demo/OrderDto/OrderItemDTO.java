package com.example.demo.OrderDto;

import java.math.BigDecimal;

 
public class OrderItemDTO {
	
	  private Long id;

	    private Long productId;      // from Product Service
	    private String productName;
	    private BigDecimal price;
	    private int quantity;
	    
	    private OrderDTO order;

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

		public OrderDTO getOrder() {
			return order;
		}

		public void setOrder(OrderDTO order) {
			this.order = order;
		}

		@Override
		public String toString() {
			return "OrderItemDTO [id=" + id + ", productId=" + productId + ", productName=" + productName + ", price="
					+ price + ", quantity=" + quantity + "]";
		}
}
