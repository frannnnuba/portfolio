package com.store.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.api.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

}
