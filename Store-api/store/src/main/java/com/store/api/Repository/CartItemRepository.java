package com.store.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Entity.CartItem;
import com.store.api.Entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    
}
