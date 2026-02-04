package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addid;
    private String houseno;
    private String city;
    private String state;
    private String country;
    private Integer zipcode;
    
	public Long getAddid() {
		return addid;
	}
	public void setAddid(Long addid) {
		this.addid = addid;
	}
	public String getHouseno() {
		return houseno;
	}
	public void setHouseno(String houseno) {
		this.houseno = houseno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getZipcode() {
		return zipcode;
	}
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "ShippingAddress [addid=" + addid + ", houseno=" + houseno + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", zipcode=" + zipcode + "]";
	}

}
