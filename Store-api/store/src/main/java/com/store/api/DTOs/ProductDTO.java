package com.store.api.DTOs;

public class ProductDTO {
    private Long id;
    private String product_name;
    private String brand_name;
    private Double price; 
    private Long stock;
    private String category;

    public ProductDTO(Long id, String product_name, String brand_name, 
        Double price, Long stock, String category) {
        this.id = id;
        this.product_name = product_name;
        this.brand_name = brand_name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
