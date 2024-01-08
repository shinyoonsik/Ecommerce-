package com.example.ordersystemconsumer.entity;

import com.example.ordersystemconsumer.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SHIPMENT")
public class ShipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHIPMENT_ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "SHIPPING_ADDRESS")
    private String shippingAddress;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private Timestamp createdAt;

    public static ShipmentEntity from(OrderDTO orderDTO){
        return ShipmentEntity.builder()
                .orderId(orderDTO.getOrderId())
                .memberId(orderDTO.getMemberId())
                .productId(orderDTO.getProductId())
                .amount(orderDTO.getAmount())
                .shippingAddress(orderDTO.getShippingAddress())
                .createdAt(orderDTO.getCreatedAt())
                .build();
    }
}
