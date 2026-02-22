package com.example.demo.Dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PagedResponse<T> {

    @JsonProperty("_embedded")
    private Embedded<T> embedded;

    @JsonProperty("page")
    private Page page;

    public List<T> getContent() {
        return embedded != null ? embedded.getContent() : null;
    }

    public int getTotalPages() {
        return page != null ? page.getTotalPages() : 0;
    }

    public long getTotalElements() {
        return page != null ? page.getTotalElements() : 0;
    }

    public int getNumber() {
        return page != null ? page.getNumber() : 0;
    }

    public int getSize() {
        return page != null ? page.getSize() : 0;
    }

    // ================= EMBEDDED =================
    public static class Embedded<T> {

        @JsonProperty("products")   // MUST match Spring Data REST JSON
        private List<T> products;

        public List<T> getContent() {
            return products;
        }
    }

    // ================= PAGE =================
    public static class Page {

        private int size;
        private long totalElements;
        private int totalPages;
        private int number;

        public int getSize() { return size; }
        public long getTotalElements() { return totalElements; }
        public int getTotalPages() { return totalPages; }
        public int getNumber() { return number; }
    }

    @Override
    public String toString() {
        return "PagedResponse [embedded=" + embedded + ", page=" + page + "]";
    }
}