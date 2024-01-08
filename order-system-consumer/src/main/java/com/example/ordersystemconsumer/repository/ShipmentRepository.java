package com.example.ordersystemconsumer.repository;

import com.example.ordersystemconsumer.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {
}
