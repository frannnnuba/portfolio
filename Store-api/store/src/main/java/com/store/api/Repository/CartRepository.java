package com.store.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

}
