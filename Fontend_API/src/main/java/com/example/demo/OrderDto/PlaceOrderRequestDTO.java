package com.example.demo.OrderDto;

public class PlaceOrderRequestDTO {
	
	private CheckoutRequestDTO checkout;
    private ShippingAddressDTO newAddress;

    public CheckoutRequestDTO getCheckout() {
        return checkout;
    }

    public void setCheckout(CheckoutRequestDTO checkout) {
        this.checkout = checkout;
    }

    public ShippingAddressDTO getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(ShippingAddressDTO newAddress) {
        this.newAddress = newAddress;
    }

	@Override
	public String toString() {
		return "PlaceOrderRequestDTO [checkout=" + checkout + ", newAddress=" + newAddress + "]";
	}
    
    
}
