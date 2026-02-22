package com.example.demo.Service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

import com.example.demo.Configuiration.RazorpayProperties;
import com.example.demo.Dto.*;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.ShippingAddress;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ShippingAddressRepo;
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

    @Autowired
    private ShippingAddressRepo shippingAddressRepo;

    // ================= CREATE ORDER =================
    @Transactional
    public Order createOrder(CheckoutRequest cusDto) throws RazorpayException {

        Order orderEntity = new Order();
        orderEntity.setRootUserId(cusDto.getRootUserId());
        orderEntity.setOrderTrackingNumber(generateTrackingNo());
        orderEntity.setOrderStatus("CREATED");

        // ================= FREEZE ADDRESS =================
        ShippingAddress shippingAddress;

        if (cusDto.getShippingAddress().getAddid() != null) {

            shippingAddress =
                    shippingAddressRepo.findByAddid(
                            cusDto.getShippingAddress().getAddid());

            if (shippingAddress == null) {
                throw new RuntimeException("Shipping address not found");
            }

        } else {

            shippingAddress = cusDto.getShippingAddress();
            shippingAddress.setRootUserId(cusDto.getRootUserId());

            // reset previous default
            changeAddressStatus(cusDto.getRootUserId());

            shippingAddress.setIsdefault(true);
            shippingAddressRepo.save(shippingAddress);
        }

        // ================= SNAPSHOT =================
        orderEntity.setDeliveryName(shippingAddress.getReceiverName());
        orderEntity.setDeliveryPhone(shippingAddress.getReceiverPhnNo());

        orderEntity.setDeliveryAddress(
                shippingAddress.getHouseno() + ", " +
                shippingAddress.getCity() + ", " +
                shippingAddress.getState() + ", " +
                shippingAddress.getCountry() + " - " +
                shippingAddress.getZipcode()
        );

        // ================= ORDER ITEMS =================
        orderEntity.setOrderItems(cusDto.getOrderItems());
        cusDto.getOrderItems().forEach(i -> i.setOrder(orderEntity));

        // ================= TOTAL =================
        BigDecimal totalPrice = cusDto.getOrderItems().stream()
                .map(i -> i.getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQty = cusDto.getOrderItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setTotalQuantity(totalQty);

        // ================= SAVE ORDER =================
        Order savedOrder = order.save(orderEntity);

        // ================= RAZORPAY =================
        JSONObject razorpayRequest = new JSONObject();
        razorpayRequest.put("amount",
                totalPrice.multiply(BigDecimal.valueOf(100)).intValue());
        razorpayRequest.put("currency", "INR");
        razorpayRequest.put("receipt",
                savedOrder.getOrderTrackingNumber());

        com.razorpay.Order razorpayOrder =
                razorpayClient.orders.create(razorpayRequest);

        savedOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        return order.save(savedOrder);
    }

    // ================= TRACKING =================
    public String generateTrackingNo() {
        return "ORD" + System.currentTimeMillis();
    }

    // ================= GET DEFAULT ADDRESS =================
    public ShippingAddress getShippingAddress(Long rootUserId) {

        ShippingAddress a1 =
                shippingAddressRepo
                        .findByRootUserIdAndIsdefault(rootUserId, true);

        if (a1 == null)
            throw new RuntimeException("Adress Not Found");

        return a1;
    }

    // ================= PAYMENT VERIFY =================
    public Order verifyRequest(PaymentVerifyRequest paymentVerify)
            throws RazorpayException {

        Order o3 = order
                .findByRazorpayOrderId(
                        paymentVerify.getRazorPayOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        String payload =
                paymentVerify.getRazorPayOrderId()
                        + "|"
                        + paymentVerify.getRazorPayPaymentId();

        boolean isValid = Utils.verifySignature(
                payload,
                paymentVerify.getRazorPaySignature(),
                razorpayProperties.getSecret());

        if (!isValid)
            throw new RuntimeException("Payment verification failed");

        o3.setRazorpayPaymentId(
                paymentVerify.getRazorPayPaymentId());
        o3.setRazorpaySignature(
                paymentVerify.getRazorPaySignature());
        o3.setOrderStatus("PAID");

        return order.save(o3);
    }

    // ================= RESET DEFAULT =================
    public boolean changeAddressStatus(Long rootuserid) {
    	if(rootuserid==null) throw new RuntimeException("User not login ");

        ShippingAddress existing =
                shippingAddressRepo
                        .findByRootUserIdAndIsdefault(
                                rootuserid, true);

        if (existing != null) {
            existing.setIsdefault(false);
            shippingAddressRepo.save(existing);
        }
        return true;
    }

    // ================= GET ALL ADDRESSES =================
    public List<ShippingAddress> getAllExistsShippingAddress(
            Long rootUserId) {

        List<ShippingAddress> list =
                shippingAddressRepo.findByRootUserId(rootUserId);

        if (list.isEmpty())
            throw new RuntimeException(
                    "shipping addresses not found");

        return list;
    }

    // ================= GET BY ID =================
    public ShippingAddress getShippingAddressById(Long id) {

        ShippingAddress existsAdd =
                shippingAddressRepo.findByAddid(id);
        

        if (existsAdd == null)
            throw new RuntimeException("Address Not Exists");

        return existsAdd;
    }

    // ================= UPDATE =================
    public String updateShippingAddress(
            ShippingAddressDTO d3) {

        ShippingAddress existing =
                shippingAddressRepo.findByAddid(d3.getAddid());

        if (existing == null)
            throw new RuntimeException("Address not found");

        existing.setCity(d3.getCity());
        existing.setCountry(d3.getCountry());
        existing.setHouseno(d3.getHouseno());
        existing.setReceiverName(d3.getReceiverName());
        existing.setReceiverPhnNo(d3.getReceiverPhnNo());
        existing.setState(d3.getState());
        existing.setZipcode(d3.getZipcode());

        shippingAddressRepo.save(existing);
        return "Address successfully updated";
    }

    // ================= DELETE =================
    public String deleteShippingAddress(Long id) {

        if (!shippingAddressRepo.existsById(id))
            throw new RuntimeException("Address not Found");

        shippingAddressRepo.deleteById(id);
        return "Address deleted successfully";
    }

    // ================= SAVE ADDRESS =================
    public ShippingAddress saveShippingAddress(
            ShippingAddressDTO d2) {

        if (d2 == null)
            throw new RuntimeException("Dto Not Found");

        ShippingAddress obj = new ShippingAddress();
        obj.setCity(d2.getCity());
        obj.setCountry(d2.getCountry());
        obj.setHouseno(d2.getHouseno());
        obj.setReceiverName(d2.getReceiverName());
        obj.setReceiverPhnNo(d2.getReceiverPhnNo());
        obj.setRootUserId(d2.getRootUserId());
        obj.setState(d2.getState());
        obj.setZipcode(d2.getZipcode());
        obj.setIsdefault(false);

        return shippingAddressRepo.save(obj);
    }
    
    public Long getRootUserId(Long rootUserId) {
    	return rootUserId;
    	
    }
    
    public List<Order> getAllOrder(Long id){
    	
    	List<Order> orderList = order.findByRootUserId(id)
    			.orElseThrow(()-> new RuntimeException("order not found"));
    	
    	
    	return orderList;
    	
    }
    
    
    
     
}
