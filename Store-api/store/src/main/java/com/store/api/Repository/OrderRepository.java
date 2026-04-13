package com.store.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
