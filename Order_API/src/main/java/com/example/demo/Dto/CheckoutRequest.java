package com.example.demo.Dto;

import java.util.List;

import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.ShippingAddress;

public class CheckoutRequest {

    // log in customer 
    private Long rootUserId;

    // Selected OR newly created address
    private ShippingAddress shippingAddress;

    // Cart items
    private List<OrderItem> orderItems;

    public Long getRootUserId() {
        return rootUserId;
    }

    public void setRootUserId(Long rootUserId) {
        this.rootUserId = rootUserId;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "CheckoutRequest [rootUserId=" + rootUserId +
                ", shippingAddress=" + shippingAddress +
                ", orderItems=" + orderItems + "]";
    }
}
