package com.example.demo.CartController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.*;
import com.example.demo.OrderDto.CheckoutRequestDTO;
import com.example.demo.OrderDto.OrderDTO;
import com.example.demo.OrderDto.PaymentVerifyRequestDTO;
import com.example.demo.OrderDto.ShippingAddressDTO;
import com.example.demo.Service.UserService;

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
    @GetMapping("/checkout")
    public String getCheckoutPage(Model model) {

        CheckoutRequestDTO checkout = new CheckoutRequestDTO();
        checkout.setShippingAddress(new ShippingAddressDTO());

        model.addAttribute("checkout", checkout);
        return "user/checkout";
    }

    // ================= PLACE ORDER (AJAX) =================
    @PostMapping("/place-Order")
    @ResponseBody
    public OrderDTO placeOrder(
            @RequestBody CheckoutRequestDTO request,
            HttpSession session) {

        session.setAttribute("Customer Name", request.getName());

        request.setOrderItems(
                productService.convertCartToOrderItem(session)
        );

        return productService.saveOrder(request);
    }

    // ================= PAYMENT VERIFY =================
    @PostMapping("/payment-verify")
    public String verifyPayment(
            @RequestBody PaymentVerifyRequestDTO request,
            Model model,
            HttpSession session) {

        OrderDTO order = productService.verifyAndUpdateOrder(request);

        model.addAttribute("orderId", order.getRazorpayOrderId());
        model.addAttribute(
                "customerName",
                session.getAttribute("Customer Name")
        );

        // OPTIONAL: clear cart after success
        session.removeAttribute("cart");

        return "payment-success"; // maps to payment-success.html
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