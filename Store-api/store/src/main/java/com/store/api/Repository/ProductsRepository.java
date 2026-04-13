package com.store.api.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Entity.Product;

public interface ProductsRepository extends JpaRepository<Product,Long> {
    public List<Product> findByCategory(String aCategory);
    public Optional<Product> findByProductName(String aName);
}
