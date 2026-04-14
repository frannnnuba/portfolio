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
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //The listed below values might change on time, so better keep a record of their
    //value at the time of the purchase

    @Column(name = "product_id",nullable = false)
    private Long product_id;

    @Column(name = "product_name",nullable = false)
    private String product_name; 

    @Column(name = "price",nullable = false)
    private Double price; 

    @Column(name = "quantity",nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
