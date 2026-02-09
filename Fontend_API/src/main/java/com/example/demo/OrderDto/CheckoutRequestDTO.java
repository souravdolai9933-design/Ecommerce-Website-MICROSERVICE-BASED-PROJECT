package com.example.demo.OrderDto;

import java.util.List;

public class CheckoutRequestDTO {
	
	   private Long cusid;
	    private String name;
	    private Long phoneno;
	    private Long rootUserId;
	    
	    
	    private ShippingAddressDTO shippingAddress;
	    
	    private List<OrderItemDTO> orderItems;

		public Long getCusid() {
			return cusid;
		}

		public void setCusid(Long cusid) {
			this.cusid = cusid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getPhoneno() {
			return phoneno;
		}

		public void setPhoneno(Long phoneno) {
			this.phoneno = phoneno;
		}

		public ShippingAddressDTO getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(ShippingAddressDTO shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public List<OrderItemDTO> getOrderItems() {
			return orderItems;
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
			return "CheckoutRequestDTO [cusid=" + cusid + ", name=" + name + ", phoneno="
					+ phoneno + ", shippingAddress=" + shippingAddress + ", orderItems=" + orderItems + "]"+" ,RootUserId "+rootUserId;
		}
		
		
}
