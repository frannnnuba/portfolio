package com.store.api.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.store.api.DTOs.CartDTO;
import com.store.api.Entity.Cart;
import com.store.api.Entity.CartItem;
import com.store.api.Mappers.MapperCart;
import com.store.api.Repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cart_repo;
    @Autowired
    private ReadProductsService read_serv;

    public Long createCart(){
        Set<CartItem> set = new HashSet<>();
        Cart cart = new Cart();
        cart.setItems_on_cart(set);
        return cart.getId();
    }

    public Set<CartItem> listCartItems(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        return cart.getItems_on_cart();
    }

    public CartDTO getCart(@PathVariable Long cartId){
         Cart cart = cart_repo.findById(cartId).orElseThrow();
         return MapperCart.toDTO(cart);
    }

    public void addItem(Long cartId, Long productId,Long amount){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        
        Optional<CartItem> existingItem = getExistingItem(productId, cart);

        if(existingItem.isPresent()){
             existingItem.get().setAmount(existingItem.get().getAmount() + amount);
        }else{
            CartItem new_CartItem = new CartItem();
            new_CartItem.setAmount(amount);
            new_CartItem.setProduct_id(productId);
            cart.getItems_on_cart().add(new_CartItem);
        }
        cart_repo.save(cart);
    }

    public void substractItem(Long cartId, Long productId,Long amount){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        Optional<CartItem> existingItem = getExistingItem(productId, cart);
        if(existingItem.isPresent()){
            Long newTotal = existingItem.get().getAmount() - amount;
            if(newTotal >0){
                existingItem.get().setAmount(newTotal);
            }else{
                removeItem(cartId, productId);
            }
        }else{
            removeItem(cartId, productId);
            throw new RuntimeException("Item isn't present on cart");
        }
    }

    public void emptyCart(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        Set<CartItem> newSet = new HashSet<>();
        cart.setItems_on_cart(newSet);
    }

    public void removeItem(Long cartId,Long prodId){
         Cart cart = cart_repo.findById(cartId).orElseThrow();
         Optional<CartItem> existingItem = getExistingItem(prodId, cart);
        if(existingItem.isPresent()){
            cart.getItems_on_cart().remove(existingItem.get());
            existingItem.get().setCart(null);
        }
    }

    public Double precalculateTotal(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
      
        return cart.getItems_on_cart()
        .stream().
        map(item ->(item.getAmount()* read_serv.priceById(item.getProduct_id()))).reduce(0.00, Double::sum);
    }

    public void setAmount(Long anAmount,Long productId,Long cartId){
        if(anAmount < 0 || !anAmount.getClass().equals(Long.class)){
            throw new RuntimeException();
        }

        Cart cart = cart_repo.findById(cartId).orElseThrow();
        
        Optional<CartItem> existingItem = getExistingItem(productId, cart);
        if(existingItem.isPresent()){
             existingItem.get().setAmount(anAmount);
        }else{
            CartItem new_CartItem = new CartItem();
            new_CartItem.setAmount(anAmount);
            new_CartItem.setProduct_id(productId);
            cart.getItems_on_cart().add(new_CartItem);
        }
        cart_repo.save(cart);
    }

    private Optional<CartItem> getExistingItem(Long productId, Cart cart) {
        return cart.getItems_on_cart()
        .stream()
        .filter(item -> item.getProduct_id().equals(productId))
        .findFirst();
    }
}
