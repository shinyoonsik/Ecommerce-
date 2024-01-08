package com.example.ordersystemstreams.repository;


import com.example.ordersystemstreams.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
