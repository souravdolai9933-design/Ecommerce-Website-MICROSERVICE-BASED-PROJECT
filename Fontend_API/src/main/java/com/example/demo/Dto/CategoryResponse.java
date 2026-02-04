package com.example.demo.Dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    // No-arg constructor (required by Jackson)
    public CategoryResponse() {
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    // ---------------- INNER CLASS ----------------

    public static class Embedded {

        @JsonProperty("productCategories")
        private List<ProductCategoryDTO> productCategories;

        public Embedded() {
        }

        public List<ProductCategoryDTO> getProductCategories() {
            return productCategories;
        }

        public void setProductCategories(List<ProductCategoryDTO> productCategories) {
            this.productCategories = productCategories;
        }
    }
}
