package com.store.api.DTOs;

import java.util.Set;

import com.store.api.Entity.OrderItem;
import com.store.api.Entity.StateOfOrder;
import com.store.api.Entity.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class OrderDTO {

    private Long id;
    private Double total;
    private StateOfOrder state;
    private Set<OrderItem> order_items;
    private Users user;
    public OrderDTO(Long id, Double total, StateOfOrder state, Set<OrderItem> order_items, Users user) {
        this.id = id;
        this.total = total;
        this.state = state;
        this.order_items = order_items;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public StateOfOrder getState() {
        return state;
    }
    public void setState(StateOfOrder state) {
        this.state = state;
    }
    public Set<OrderItem> getOrder_items() {
        return order_items;
    }
    public void setOrder_items(Set<OrderItem> order_items) {
        this.order_items = order_items;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }

    
}
