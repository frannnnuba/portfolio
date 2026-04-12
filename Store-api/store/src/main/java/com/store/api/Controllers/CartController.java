package com.store.api.Controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart")
public class CartController {

    private final Set<CartItemController> productos;

    public CartController(Set<CartItemController> productos) {
        this.productos = productos;
    } 

    
}
