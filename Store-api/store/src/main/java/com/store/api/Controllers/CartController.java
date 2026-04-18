package com.store.api.Controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.DTOs.CartDTO;
import com.store.api.DTOs.CartItemDTO;
import com.store.api.Entity.Users;
import com.store.api.Service.CartService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartService cart_serv;

    public CartController( CartService cart_serv) {
        this.cart_serv = cart_serv;
    } 

    @GetMapping("/list/{cart_id}")
    @PreAuthorize("hasAnyRole({'ADMIN','USER'})")
    public Set<CartItemDTO> listCartItems(@PathVariable Long cartId){
        return cart_serv.listCartItems(cartId);
    }

    @GetMapping("total_of_cart/{cartId}")
    @PreAuthorize("hasAnyRole({'ADMIN','USER'})")
    public Double precalculateTotal(@PathVariable Long cartId){
        return cart_serv.precalculateTotal(cartId);
    }

    @GetMapping("/get_cart/{cartId}")
    @PreAuthorize("hasAnyRole({'ADMIN','USER'})")
    public CartDTO getCart(@PathVariable Long cartId){
        return cart_serv.getCart(cartId);
    }
    
    ///////////////PostMethods//////////////
    @PostMapping("/create")
    @PreAuthorize("hasAnyRol({'ADMIN','USER'})")
    public CartDTO createCart(@AuthenticationPrincipal Users user){
        return cart_serv.createCart(user);
    }

    @PostMapping("/add_item/{productId}/on_cart/{cartId}/{amount}")
    @PreAuthorize("hasRole('USER')")
    public void addItem(@PathVariable Long cartId,@PathVariable Long productId,@PathVariable Long amount){
        cart_serv.addItem(cartId, productId, amount);
    }

    @PostMapping("/increment/{productId}/on_cart/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public void incrementItem(@PathVariable Long cartId,@PathVariable Long productId){
        cart_serv.addItem(cartId, productId, 1L);
    }

    @PostMapping("/substract/{productId}/on_cart/{cartId}/{amount}")
    @PreAuthorize("hasRole('USER')")
    public void substractItem(@PathVariable Long cartId,@PathVariable Long productId,@PathVariable Long amount){
        cart_serv.substractItem(cartId, productId, amount);
    }

    @PostMapping("/decrement_item/{productId}/on_cart/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public void decrementItem(@PathVariable Long cartId,@PathVariable Long productId){
        cart_serv.substractItem(cartId, productId, 1L);
    }

    //////////////DeleteMethods//////////////////
    @DeleteMapping("/empty/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public void emptyCart(@PathVariable Long cartId){
        cart_serv.emptyCart(cartId);
    }

    @DeleteMapping("remove_from/{cartId}/{prodId}")
    @PreAuthorize("hasRole('USER')")
    public void removeItem(@PathVariable Long cartId,@PathVariable Long prodId){
        cart_serv.removeItem(cartId, prodId);
    }

    ///////////PatchMethods//////////////////////
    @PatchMapping("/set_amount/{anAmount}/of_item/{cartItemId}/on_cart/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public void setAmount(@PathVariable Long anAmount,@PathVariable Long itemId,@PathVariable Long cartId){
        cart_serv.setAmount(anAmount,itemId,cartId);
    }
   
}
