package com.store.api.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_item")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id",nullable = false)
    private Long product_id;

    @Column(name="price",nullable = false)
    private Double price;

    @Column(name="amount",nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name ="cart_id")
    private Cart cart;
    
}

