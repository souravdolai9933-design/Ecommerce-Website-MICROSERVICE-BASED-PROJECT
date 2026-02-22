 package com.example.demo.CartController;
 

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Dto.*;
import com.example.demo.OrderDto.CheckoutRequestDTO;
import com.example.demo.OrderDto.OrderDTO;
import com.example.demo.OrderDto.PaymentVerifyRequestDTO;
import com.example.demo.OrderDto.PlaceOrderRequestDTO;
import com.example.demo.OrderDto.ShippingAddressDTO;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService productService;

    // ================= ADD TO CART =================
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        Cart cart = getCart(session);
        ProductDTO product = productService.getProduct(id);
        cart.addItem(product);

        return "redirect:/cart/view";
    }

    // ================= INCREASE =================
    @GetMapping("/increase/{id}")
    public String increase(@PathVariable Long id, HttpSession session) {

        getCart(session).increase(id);
        return "redirect:/cart/view";
    }

    // ================= DECREASE =================
    @GetMapping("/decrease/{id}")
    public String decrease(@PathVariable Long id, HttpSession session) {

        getCart(session).decrease(id);
        return "redirect:/cart/view";
    }

    // ================= VIEW CART =================
    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {

        Cart cart = getCart(session);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("grandTotal", cart.getGrandTotal());

        return "cart";
    }

    // ================= CHECKOUT PAGE =================
 // ================= CHECKOUT PAGE =================
    @GetMapping("/checkout")
    public String getCheckoutPage(Model model , HttpServletRequest request ) {

        System.out.println("***********I am inside Get Check Out page Method*******");

        HttpSession session = request.getSession(true);

        Cart cart2 = (Cart) session.getAttribute("cart");

        if(cart2 != null){
            Long totalItem = cart2.getItems().stream().count();
            BigDecimal totalAmount = cart2.getGrandTotal();

            model.addAttribute("totalItems", totalItem);
            model.addAttribute("totalPrice", totalAmount);
        }

        Long rootUserId = productService.getRootUserId();

        // ================= EXISTING ADDRESS =================
        List<ShippingAddressDTO> shippingAddressList = new ArrayList<>();
        boolean hasExistingAddress = false;

        try {
            shippingAddressList = productService.getShippingAddress(rootUserId, request);

            if (shippingAddressList != null && !shippingAddressList.isEmpty()) {
                hasExistingAddress = true;
                model.addAttribute("shippingAdresslist", shippingAddressList);
            }

        } catch (Exception ex) {
            System.out.println("No Existing Address Found");
        }

        model.addAttribute("hasExistingAddress", hasExistingAddress);


        // ================= DEFAULT ADDRESS =================
        CheckoutRequestDTO defaultcheckout = null;
        boolean hasDefaultAddress = false;

        try {
            ShippingAddressDTO defaultAddress =
                    productService.getDefaultAddress(rootUserId, request);

            if (defaultAddress != null) {
                defaultcheckout = new CheckoutRequestDTO();
                defaultcheckout.setShippingAddress(defaultAddress);

                hasDefaultAddress = true;
                model.addAttribute("defaultcheckout", defaultcheckout);
            }

        } catch (Exception e) {
            System.out.println("Default Address not found...");
        }

        model.addAttribute("hasDefaultAddress", hasDefaultAddress);


        // ================= NEW ADDRESS OBJECT =================
        CheckoutRequestDTO newCheckout = new CheckoutRequestDTO();
        newCheckout.setShippingAddress(new ShippingAddressDTO());
        model.addAttribute("newcheckout", newCheckout);

        System.out.println("*****Method END***********");

        return "user/checkout";
    }

 // ================= PLACE ORDER (AJAX) =================
    @PostMapping("/place-Order")
    @ResponseBody
    public OrderDTO placeOrder(
            @RequestBody PlaceOrderRequestDTO payload,
            HttpSession session,
            HttpServletRequest httpRequest) {

        Long rootUserId = productService.getRootUserId();
        
        
        System.out.println("Payload Respon se ->:"+payload);
        
        CheckoutRequestDTO request = payload.getCheckout();
        request.setRootUserId(rootUserId);
        
        System.out.println("Checkout Req "+request);

        // Attach cart items
        request.setOrderItems(
            productService.convertCartToOrderItem(session)
        );
        System.out.println("Address id -->"+request.getExistingAddressId());

        // ================= EXISTING ADDRESS =================
        if (request.getExistingAddressId() != null) {
        	
            ShippingAddressDTO address =
                productService.getShippingAddressById(
                    request.getExistingAddressId(), httpRequest
                );

            request.setShippingAddress(address);
        }

        // ================= NEW ADDRESS =================
        if (payload.getNewAddress() != null) {

            ShippingAddressDTO newAddress = payload.getNewAddress();
            newAddress.setRootUserId(rootUserId);

            // SAVE address first
            //ShippingAddressDTO savedAddress = productService.saveShippingAddress(request.getShippingAddress(), httpRequest);
            //
            request.setShippingAddress(newAddress);
            
            System.out.println("Save shipping Address :"+newAddress);

          //  request.setShippingAddress(savedAddress);
        }
        
        System.out.println("Finel Checkout Request Dto Before sending Backend "+request);

        
        return productService.saveOrder(request, httpRequest);
    }

    
    @PostMapping("/payment-verify")
    public String verifyPayment(
            @RequestBody PaymentVerifyRequestDTO request,
            Model model,
            HttpSession session, HttpServletRequest request2) {
    	System.out.println("*********Payment verify methode start****");
    	
    	System.out.println("Fonntend Payment Verify Req :--"+request);

        OrderDTO order = productService.verifyAndUpdateOrder(request,request2);

        model.addAttribute("orderId", order.getRazorpayOrderId());
        model.addAttribute(
                "customerName",
                session.getAttribute("Customer Name")
        );

        // OPTIONAL: clear cart after success
        session.removeAttribute("cart");
        System.out.println("********Method End *********");

        return "payment-success"; // maps to payment-success.html
    }
    
    @GetMapping("/address/edit/{id}")
    public String getEditAddress(@PathVariable("id") Long id,
                                 HttpServletRequest req,
                                 Model model) {

        System.out.println("------ I am inside getEditAddress ------");

        ShippingAddressDTO editAddress =
                productService.getShippingAddressById(id, req);

        System.out.println("Edit Address :-> " + editAddress);

        model.addAttribute("address", editAddress);
        return "user/edit-form";
    }

    
    @PostMapping("/address/update")
    public String updateAddress(@ModelAttribute ShippingAddressDTO address,
                                HttpServletRequest req, Model model,  RedirectAttributes redirectAttributes) {
    	
    	System.out.println("*******I am inside updateAdress methode******");

       String msg =  productService.updateShippingAddress(req, address);
       
       System.out.println("Backend Message :->"+msg);
       
       redirectAttributes.addFlashAttribute("sucmsg",msg);; 
        
        
       return "redirect:/cart/checkout";
    }
    
    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id, HttpServletRequest req ,  RedirectAttributes redirectAttributes) {
    	
    	System.out.println("*****I am inside Delete methode*****");
    	
      String msg = productService.deleteShippingAddress(req, id);
      
      System.out.println("backend msg :"+msg);
      
      redirectAttributes.addFlashAttribute("sucmsg",msg);
      
      return "redirect:/cart/checkout";
    	
    }
    
    // ================= CART HELPER =================
    private Cart getCart(HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}