package com.store.api.Service;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.store.api.DTOs.CartDTO;
import com.store.api.DTOs.CartItemDTO;
import com.store.api.Entity.Cart;
import com.store.api.Entity.CartItem;
import com.store.api.Entity.Product;
import com.store.api.Entity.Users;
import com.store.api.Mappers.MapperCart;
import com.store.api.Mappers.MapperCartItem;
import com.store.api.Repository.CartRepository;
import com.store.api.Repository.ProductsRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    private CartRepository cart_repo;
    private ProductsRepository prod_repo;

    public CartService(CartRepository cart_repo,ProductsRepository prod_repo){
        this.cart_repo = cart_repo;
        this.prod_repo = prod_repo;  
    }

    public CartDTO createCart(Users user){
        Cart cart = new Cart();
        cart.setUser(user);
        cart_repo.save(cart);
        return MapperCart.toDTO(cart);
    }

    public Set<CartItemDTO> listCartItems(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        return cart.getItems_on_cart().stream().map(MapperCartItem::toDto).
        collect(Collectors.toSet());
    }

    public CartDTO getCart(Long cartId){
         Cart cart = cart_repo.findById(cartId).orElseThrow(()-> new EntityNotFoundException());
         return MapperCart.toDTO(cart);
    }

    public Double precalculateTotal(Long cartId){
        Cart cart = cart_repo.findById(cartId).orElseThrow();
        double total = 0.0;

        for (CartItem item : cart.getItems_on_cart()) {
            Product prod = prod_repo.findById(item.getProduct_id())
                .orElseThrow();

            total += prod.getPrice() * item.getAmount();
        }
        return total;
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
