package com.example.ordersystemproducer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "ORDER_DETAIL")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "SHIPPING_ADDRESS")
    private String shippingAddress;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp
    private Timestamp createdAt;
}
