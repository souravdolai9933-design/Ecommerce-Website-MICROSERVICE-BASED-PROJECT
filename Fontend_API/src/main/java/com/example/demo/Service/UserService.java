package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Dto.Cart;
import com.example.demo.Dto.CartItemDTO;
import com.example.demo.Dto.CategoryResponse;
import com.example.demo.Dto.PagedResponse;
import com.example.demo.Dto.ProductCategoryDTO;
import com.example.demo.Dto.ProductDTO;

import jakarta.servlet.http.HttpSession;

import com.example.demo.OrderDto.*;
 

@Service
public class UserService {
	
	@Autowired
	private WebClient webclient;
	
	@Autowired
	private Cart cart;
	
	
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
	
	public OrderDTO saveOrder(CheckoutRequestDTO checkoutRequest) {

	    return webclient
	            .post()
	            .uri(uriBuilder -> uriBuilder
	                    .path("/order/create")
	                    .build())
	            .bodyValue(checkoutRequest)
	            .retrieve()
	            .bodyToMono(OrderDTO.class)
	            .block();   // blocks until response arrives
	  }
	
	public OrderDTO verifyAndUpdateOrder (PaymentVerifyRequestDTO paymentVerify) {
		
		return webclient.post()
				.uri(i->i.path("/order/payment-verify")
						.build())
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
	}
