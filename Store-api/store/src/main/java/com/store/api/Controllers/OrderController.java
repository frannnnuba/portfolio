package com.store.api.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.DTOs.OrderDTO;
import com.store.api.DTOs.OrderItemDTO;
import com.store.api.Service.OrderService;


import java.util.Set;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService order_serv;

    
    public OrderController(OrderService order_serv) {
        this.order_serv = order_serv;
    }

    //balance de ordenes,esta aprobada? 
    @GetMapping("/approved/{id}")
    public boolean isApproved(@PathVariable Long id) {
        return order_serv.isApproved(id);
    }
    
    @GetMapping("/balance_of_user/{userId}")
    public Double getBalance(@PathVariable Long userId){
        return order_serv.getBalance(userId);
    }

    @GetMapping("/list_items/{orderId}")
    public Set<OrderItemDTO> listItems(@PathVariable Long orderId) {
        return order_serv.listItems(orderId);
    }
    
    @GetMapping("/list_orders/{userId}")
    public Set<OrderDTO> listOrders(@PathVariable Long userId){
        return order_serv.listOrders(userId);
    }
}
