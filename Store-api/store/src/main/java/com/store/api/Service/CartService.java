package com.store.api.Service;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.store.api.DTOs.CartDTO;
import com.store.api.Entity.Cart;
import com.store.api.Entity.CartItem;
import com.store.api.Mappers.MapperCart;
import com.store.api.Repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    private CartRepository cart_repo;

    public CartService(CartRepository cart_repo){
        this.cart_repo = cart_repo;
    }

    public Long createCart(){
        Cart cart = new Cart();
        cart_repo.save(cart);
        return cart.getId();
    }

    public Set<CartItem> listCartItems(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        return cart.getItems_on_cart();
    }

    public CartDTO getCart(Long cartId){
         Cart cart = cart_repo.findById(cartId).orElseThrow();
         return MapperCart.toDTO(cart);
    }

    public Double precalculateTotal(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        return cart.precalculateTotal();
    }

    @Transactional
    public void addItem(Long cartId, Long productId,Long amount){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        cart.addItem( productId, amount);
    }

    @Transactional
    public void substractItem(Long cartId, Long productId,Long amount){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        cart.substractItem( productId, amount);
    }

    @Transactional
    public void emptyCart(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        cart.emptyCart();
    }

    @Transactional
    public void removeItem(Long cartId,Long prodId){
         Cart cart = cart_repo.findById(cartId).orElseThrow();
         cart.removeItem(prodId);
    }

    @Transactional
    public void setAmount(Long anAmount,Long productId,Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        cart.setAmount(anAmount, productId);
    }

}
