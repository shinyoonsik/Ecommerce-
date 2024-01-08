package com.example.ordersystemconsumer.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {
    private Long orderId;
    private Long memberId;
    private String productId;
    private Integer amount;
    private String shippingAddress;
    private Timestamp createdAt;
}


