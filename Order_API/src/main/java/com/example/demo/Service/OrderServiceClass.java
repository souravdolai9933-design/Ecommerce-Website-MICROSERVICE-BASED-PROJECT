package com.example.demo.Service;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Configuiration.RazorpayProperties;
import com.example.demo.Dto.*;
 
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.ShippingAddress;
import com.example.demo.Repository.OrderRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
@Service
public class OrderServiceClass {
	
	 @Autowired
	 private RazorpayClient razorpayClient;
	
	@Autowired
	private OrderRepository order;
	
	@Autowired
	private RazorpayProperties razorpayProperties;
	
	public Order createOrder(CheckoutRequest cusDto) throws RazorpayException {
		
	Order o1 = new Order();
	 
	 o1.setRootUserId(cusDto.getRootUserId());
	 o1.setOrderTrackingNumber(generateTrackingNo());
	 o1.setOrderStatus("CREATED");
	  
	 
	 System.out.println("Order data set processing...");
	 // set shipping adress into order for adress frezz
	 ShippingAddress addr = cusDto.getShippingAddress();
	 o1.setDeliveryName(addr.getReceiverName());
	 o1.setDeliveryPhone(addr.getReceiverPhnNo());
	 o1.setDeliveryAddress(
             addr.getHouseno() + ", " +
             addr.getCity() + ", " +
             addr.getState() + ", " +
             addr.getCountry() + " - " +
             addr.getZipcode()
     );
	 
	 // set Order Items
	 o1.setOrderItems(cusDto.getOrderItems());
	 
	 // Set orderid for each order item
	 cusDto.getOrderItems().stream().forEach(i->i.setOrder(o1));
	 
	 BigDecimal totalPrice = cusDto.getOrderItems().stream()
             .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
             .reduce(BigDecimal.ZERO, BigDecimal::add);

     int totalQty = cusDto.getOrderItems().stream()
             .mapToInt(OrderItem::getQuantity).sum();
     
     o1.setTotalPrice(totalPrice);
     o1.setTotalQuantity(totalQty);
     
     System.out.println("Before save order data into database...");
     System.out.println("order api data "+o1);
     
          Order savedOrder = order.save(o1);
          
      System.out.println("order is created...");
          //Razor Pay Order Creation
      JSONObject json = new JSONObject();
      json.put("amount", totalPrice.multiply(BigDecimal.valueOf(100)).intValue()); // paise
      json.put("currency", "INR");
      json.put("receipt", o1.getOrderTrackingNumber());

      com.razorpay.Order razorOrder = razorpayClient.orders.create(json);

      System.out.println("Razor pay order is created sucessfully...");
      savedOrder.setRazorpayOrderId(razorOrder.get("id"));
      System.out.println("Before save order..."+savedOrder);
      return order.save(savedOrder);
	}
	
	public String generateTrackingNo() {
		return "ORD"+System.currentTimeMillis();
	}
	
	// Payment verify methode
	public Order verifyRequest(PaymentVerifyRequest paymentVerify) throws RazorpayException {

	    // 1️⃣ Find order safely
	    Order o3 = order.findByRazorpayOrderId(
	            paymentVerify.getRazorPayOrderId()
	    ).orElseThrow(() -> new RuntimeException("Order not found"));

	    // 2️⃣ Create payload
	    String payload =
	            paymentVerify.getRazorPayOrderId()
	            + "|"
	            + paymentVerify.getRazorPayPaymentId();

	    // 3️⃣ Verify signature using REAL SECRET
	    boolean isValid = Utils.verifySignature(
	            payload,
	            paymentVerify.getRazorPaySignature(),
	            razorpayProperties.getSecret()   // ✅ REAL SECRET
	    );

	    if (!isValid) {
	        throw new RuntimeException("Payment verification failed");
	    }

	    // 4️⃣ Update order
	    o3.setRazorpayPaymentId(paymentVerify.getRazorPayPaymentId());
	    o3.setRazorpaySignature(paymentVerify.getRazorPaySignature());
	    o3.setOrderStatus("PAID");

	    return order.save(o3);
	}

}
