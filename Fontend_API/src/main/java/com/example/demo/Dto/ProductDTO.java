package com.example.demo.Dto;

import java.math.BigDecimal;

public class ProductDTO {
	
    private Long id;
	
	private String name;
	
	private String description;
	
	private String title;
	
	private BigDecimal price;
	
	private int unit_in_stocks;
	
	private String imgurl;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getUnit_in_stocks() {
		return unit_in_stocks;
	}

	public void setUnit_in_stocks(int unit_in_stocks) {
		this.unit_in_stocks = unit_in_stocks;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", title=" + title
				+ ", price=" + price + ", unit_in_stocks=" + unit_in_stocks + ", imgurl=" + imgurl + ", active="
				+ active + "]";
	}
	
	
}
