package com.example.demo.Service;

import java.util.ArrayList;
import org.springframework.http.HttpHeaders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.Dto.Cart;
import com.example.demo.Dto.CartItemDTO;
import com.example.demo.Dto.CategoryResponse;
import com.example.demo.Dto.PagedResponse;
import com.example.demo.Dto.ProductCategoryDTO;
import com.example.demo.Dto.ProductDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.OrderDto.*;
import com.example.demo.RootcustomerDTO.ErrorResponse;
import com.example.demo.RootcustomerDTO.LoginUserRequestDTO;
import com.example.demo.RootcustomerDTO.RegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

 
 
 

@Service
public class UserService {
	
	@Autowired
	private WebClient webclient;
	
	@Autowired
	private Cart cart;
	
	 private final ObjectMapper mapper = new ObjectMapper();
	
	
	public List<ProductDTO> getAllProduct(int page ,int size){
		
		PagedResponse<ProductDTO> response =
                webclient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/api/product")
                                .queryParam("page", page)
                                .queryParam("size", size)
                                .build())
                        .retrieve()
                        .bodyToMono(PagedResponse.class)
                        .block();
		
		System.out.println("Response :"+response.getContent());

		return response.getContent();
		
	}
	
	public List<ProductDTO> getProductByCategory(Long categoryId, int page, int size) {

	    PagedResponse<ProductDTO> response =
	            webclient.get()
	                    .uri(uriBuilder -> uriBuilder
	                            .path("/api/product/search/findByCategoryId") // ✅ FIXED
	                            .queryParam("id", categoryId)                  // ✅ REQUIRED
	                            .queryParam("page", page)
	                            .queryParam("size", size)
	                            .build())
	                    .retrieve()
	                    .bodyToMono(new ParameterizedTypeReference<PagedResponse<ProductDTO>>() {})
	                    .block();

	    System.out.println("Content :--- " + response.getContent());

	    return response.getContent();
	}
	
	 
	public List<ProductCategoryDTO> getAllCategory() {

	    CategoryResponse response = webclient.get()
	            .uri(uriBuilder -> uriBuilder
	                    .path("/api/product_category")
	                    .build())
	            .retrieve()
	            .bodyToMono(CategoryResponse.class)
	            .block();
	    System.out.println("product :"+response.getEmbedded().getProductCategories());

	    return response.getEmbedded().getProductCategories();
	}
	
	
	public ProductDTO getProduct(Long id) {

	    ProductDTO response =
	            webclient.get()
	                    .uri(uriBuilder -> uriBuilder
	                            .path("/api/product/{id}")
	                            .build(id))
	                    .retrieve()
	                    .bodyToMono(ProductDTO.class)
	                    .block();

	    System.out.println("Response: " + response);
	    return response;
	  }	
	
	public Long getRootUserId( ) {
	    return webclient.get()
	            .uri("/auth/root-id")
	            .retrieve()
	            .bodyToMono(Long.class)
	            .block();
	}

	public OrderDTO saveOrder(CheckoutRequestDTO checkoutRequest, HttpServletRequest request) {

	    System.out.println("Controller Inside save order Methode....");

	    // ✅ Get token from session
	    String authHeader = getAuthToken(request);

	    System.out.println("Auth Header Generated Successfully: " + authHeader);

	    return webclient
	            .post()
	            .uri(uriBuilder -> uriBuilder
	                    .path("/order/create")
	                    .build())
	            .header(HttpHeaders.AUTHORIZATION, authHeader)
	            .bodyValue(checkoutRequest)
	            .retrieve()
	            .bodyToMono(OrderDTO.class)
	            .block();
	}

	
	public OrderDTO verifyAndUpdateOrder (PaymentVerifyRequestDTO paymentVerify, HttpServletRequest request) {
		
		String authHeader = getAuthToken(request);
		
		return webclient.post()
				.uri(i->i.path("/order/payment-verify")
						.build())
				.header(HttpHeaders.AUTHORIZATION,authHeader)
				.bodyValue(paymentVerify)
				.retrieve()
				.bodyToMono(OrderDTO.class)
				.block();
	}
	
	public List<OrderItemDTO> convertCartToOrderItem(HttpSession session){
		
		Cart cart2 = (Cart) session.getAttribute("cart");
		
		
		List<OrderItemDTO> orderItem = new ArrayList<>();
		
		for(CartItemDTO d3:cart2.getItems()) {
			
			OrderItemDTO d5 = new OrderItemDTO();
			d5.setProductId(d3.getProductId());
			d5.setPrice(d3.getPrice());
			d5.setProductName(d3.getTitle());
			d5.setQuantity(d3.getQuantity());
			orderItem.add(d5);
		}
		System.out.println("Order item "+orderItem);
		return orderItem;
		
	     }
	  
	public String userRegistration(RegistrationRequestDTO regisDTO) {

	    return webclient.post()
	            .uri(uriBuilder -> uriBuilder.path("/auth/register").build())
	            .bodyValue(regisDTO)
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();
	     }
	
	// Calling Customer Api Login Methode
	public String userLogin(LoginUserRequestDTO dto, HttpServletRequest request) {


		System.out.println("Controller inside User log in");
		

            String token = webclient.post()
                    .uri(uri -> uri.path("/auth/login").build())
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            HttpSession session = request.getSession(true);
            
            System.out.println("Token generate Sucessfully Token :"+token );
            session.setAttribute("JWT_TOKEN", token);
			return token;
    }
	
	// Return Jwt Token
	private String getAuthToken(HttpServletRequest request) {
		
		System.out.println("Hit Get Auth Token methode...");

	    HttpSession session = request.getSession(false);

	    if (session == null) {
	        throw new RuntimeException("User not logged in");
	    }

	    String token = (String) session.getAttribute("JWT_TOKEN");
	    
	    System.out.println("Jwt Token :"+token);

	    if (token == null) {
	        throw new RuntimeException("JWT token missing");
	    }

	    return "Bearer " + token;
	}

}
