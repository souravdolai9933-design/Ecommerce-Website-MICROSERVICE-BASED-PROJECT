package com.example.demo.Entity.Dto;

public class ProductCategoryDto {
	
	private Long id;
	private String name;
	
	public ProductCategoryDto(Integer id2, String name2) {
		 
	}
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
}
