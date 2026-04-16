package com.store.api.Entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items_on_cart = new HashSet<>();
    
    @OneToOne
    private Users user;

    
    /////METHODS/////
    //public Double precalculateTotal(){
      //  return items_on_cart
        //ap(item ->(item.getAmount()* item.getPrice())).reduce(0.00, Double::sum);
    //}

    public Optional<CartItem> getExistingItem(Long productId) {
        return items_on_cart
        .stream()
        .filter(item -> item.getProduct_id().equals(productId))
        .findFirst();
    }

    public void addItem( Long productId,Long amount){
        Optional<CartItem> existingItem = getExistingItem(productId);

        if(existingItem.isPresent()){
             existingItem.get().setAmount(existingItem.get().getAmount() + amount);
        }else{
            CartItem new_CartItem = new CartItem();
            new_CartItem.setAmount(amount);
            new_CartItem.setProduct_id(productId);
            items_on_cart.add(new_CartItem);
            new_CartItem.setCart(this);
        }
    }

    public void substractItem(Long productId,Long amount){
        Optional<CartItem> existingItem = getExistingItem(productId);
        if(existingItem.isPresent()){
            Long newTotal = existingItem.get().getAmount() - amount;
            if(newTotal >0){
                existingItem.get().setAmount(newTotal);
            }else{
                removeItem(productId);
            }
        }else{
            removeItem(productId);
            throw new EntityNotFoundException();
        }
    }

    public void emptyCart(){
        items_on_cart.forEach(i->i.setCart(null));
        items_on_cart.clear();;
    }

    public void removeItem(Long prodId){
    Optional<CartItem> existingItem = getExistingItem(prodId);
    if(existingItem.isPresent()){
            items_on_cart.removeIf(i -> i.getProduct_id().equals(prodId));
            existingItem.get().setCart(null);
        }
    }

    public void setAmount(Long anAmount,Long productId){
        if(anAmount <= 0){
            throw new IllegalArgumentException();
        }

        Optional<CartItem> existingItem = getExistingItem(productId);
        if(existingItem.isPresent()){
             existingItem.get().setAmount(anAmount);
        }else{
            CartItem new_CartItem = new CartItem();
            new_CartItem.setAmount(anAmount);
            new_CartItem.setProduct_id(productId);
            items_on_cart.add(new_CartItem);
            new_CartItem.setCart(this);
        }
    }
}