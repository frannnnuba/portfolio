package com.store.api.DTOs;

import java.util.Set;

import com.store.api.Entity.CartItem;

public class CartDTO {
    private Long id;
    private Set<CartItem> items_on_cart;
    public CartDTO(Long id, Set<CartItem> items_on_cart) {
        this.id = id;
        this.items_on_cart = items_on_cart;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Set<CartItem> getItems_on_cart() {
        return items_on_cart;
    }
    public void setItems_on_cart(Set<CartItem> items_on_cart) {
        this.items_on_cart = items_on_cart;
    }
    
}
