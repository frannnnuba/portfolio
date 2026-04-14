package com.store.api.Mappers;

import com.store.api.DTOs.CartDTO;
import com.store.api.Entity.Cart;

public class MapperCart {

    public static CartDTO toDTO(Cart cart){
        return new CartDTO(cart.getId(), cart.getItems_on_cart());
    }

    public static Cart toEntity(CartDTO cartDto){
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setItems_on_cart(cartDto.getItems_on_cart());
        return cart; 
    }
}
