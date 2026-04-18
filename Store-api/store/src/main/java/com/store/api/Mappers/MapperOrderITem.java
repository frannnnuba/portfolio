package com.store.api.Mappers;

import com.store.api.DTOs.OrderItemDTO;
import com.store.api.Entity.OrderItem;

public class MapperOrderITem {

    public static OrderItemDTO toDto(OrderItem orderItem){
        return new OrderItemDTO(orderItem.getId(),
         orderItem.getProduct_id(), orderItem.getProduct_name(),
          orderItem.getPrice(), orderItem.getQuantity(), orderItem.getOrder().getId());
    }
}
