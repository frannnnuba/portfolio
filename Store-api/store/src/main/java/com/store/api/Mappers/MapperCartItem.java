package com.store.api.Mappers;

import com.store.api.DTOs.CartItemDTO;
import com.store.api.Entity.CartItem;

public class MapperCartItem {

    public static CartItemDTO toDto(CartItem ci){
        return new CartItemDTO(ci.getId(), ci.getProduct_id(), 
        ci.getPrice(), ci.getAmount(), ci.getCart());
    }
}
