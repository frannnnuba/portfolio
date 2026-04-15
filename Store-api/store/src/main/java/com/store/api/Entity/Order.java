package com.store.api.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    private Long id;

    @Column(name="total",nullable = false)
    private Double total;

    @Column(name="state",nullable = false)
    private StateOfOrder state;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> order_items; 

    @ManyToOne
    @JoinColumn(name="user",nullable = false)
    private Users user;


    
    public void addItem(OrderItem oi){
        
    }
}
