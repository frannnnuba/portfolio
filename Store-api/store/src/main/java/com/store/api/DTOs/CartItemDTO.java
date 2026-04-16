package com.store.api.DTOs;

import com.store.api.Entity.Cart;

public class CartItemDTO {
    private Long id;
    private Long product_id;
    private Double price;
    private Long amount;
    private Cart cart;
    public CartItemDTO(Long id, Long product_id, Double price, Long amount, Cart cart) {
        this.id = id;
        this.product_id = product_id;
        this.price = price;
        this.amount = amount;
        this.cart = cart;
    }
    public Long getId() {
        return id;
    }
    
    public Long getProduct_id() {
        return product_id;
    }
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    

}
