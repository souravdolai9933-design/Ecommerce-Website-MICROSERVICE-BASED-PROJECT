package com.example.demo.OrderDto;

import java.util.List;

 

public class CheckoutRequestDTO {
	
	 // log in customer 
    private Long rootUserId;

    // Selected OR newly created address
    private ShippingAddressDTO shippingAddress;
    
     private Long  existingAddressId;
     

    // Cart items
    private List<OrderItemDTO> orderItems;

    
		public ShippingAddressDTO getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(ShippingAddressDTO shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public List<OrderItemDTO> getOrderItems() {
			return orderItems;
		}

		
		public Long getExistingAddressId() {
			return existingAddressId;
		}

		public void setExistingAddressId(Long existingAddressId) {
			this.existingAddressId = existingAddressId;
		}

		public Long getRootUserId() {
			return rootUserId;
		}

		public void setRootUserId(Long rootUserId) {
			this.rootUserId = rootUserId;
		}

		public void setOrderItems(List<OrderItemDTO> orderItems) {
			this.orderItems = orderItems;
		}

		@Override
		public String toString() {
			return "CheckoutRequestDTO [rootUserId=" + rootUserId + ", shippingAddress=" + shippingAddress
					+ ", orderItems=" + orderItems + "]";
		}
}
