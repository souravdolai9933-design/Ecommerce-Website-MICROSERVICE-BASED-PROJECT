package com.example.demo.Dto;

import java.util.List;

import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.ShippingAddress;

public class CheckoutRequest {
	
	
	    private Long cusid;
	    private String name;
	    private Long phoneno;
	    private Long rootUserId;
	    
	    private ShippingAddress shippingAddress;
	    
	    private List<OrderItem> orderItems;


	    
	    
		public Long getRootUserId() {
			return rootUserId;
		}


		public void setRootUserId(Long rootUserId) {
			this.rootUserId = rootUserId;
		}


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

		public List<OrderItem> getOrderItems() {
			return orderItems;
		}


		public void setOrderItems(List<OrderItem> orderItems) {
			this.orderItems = orderItems;
		}


		public ShippingAddress getShippingAddress() {
			return shippingAddress;
		}


		public void setShippingAddress(ShippingAddress shippingAddress) {
			this.shippingAddress = shippingAddress;
		}


		@Override
		public String toString() {
			return "CheckoutRequest [cusid=" + cusid + ", name=" + name + ", phoneno="
					+ phoneno + ", shippingAddress=" + shippingAddress + ", orderItems=" + orderItems + "]";
		}
		 
}
