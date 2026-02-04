package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.Entity.ProductCategory;

@RepositoryRestResource(path = "product_category")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
