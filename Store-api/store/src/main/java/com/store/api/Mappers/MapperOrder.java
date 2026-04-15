package com.store.api.Mappers;

import com.store.api.DTOs.OrderDTO;
import com.store.api.Entity.Order;

public class MapperOrder {

    public static OrderDTO toDto(Order order){
        return new OrderDTO(order.getId(), order.getTotal(),
             order.getState(), order.getOrder_items(), order.getUser());
    }
}
