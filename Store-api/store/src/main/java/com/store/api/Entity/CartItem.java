package com.store.api.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cat_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id",nullable = false)
    private Long product_id;

    @Column(name="amount",nullable = false)
    private Long amount;


    @Column(name="cart",nullable = false)
    private Cart cart;

    public Long getProduct_id() {
        return product_id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
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

