package com.example.ordersystemproducer.repository;

import com.example.ordersystemproducer.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
