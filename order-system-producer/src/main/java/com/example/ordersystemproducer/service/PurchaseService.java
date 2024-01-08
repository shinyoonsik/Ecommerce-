package com.example.ordersystemproducer.service;

import com.example.ordersystemproducer.dto.OrderDTO;
import com.example.ordersystemproducer.entity.OrderEntity;
import com.example.ordersystemproducer.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final static String ORDER_COMPLETE_TOPIC = "order.complete.v1";


    private final OrderRepository orderRepository;
    private final KafkaTemplate kafkaTemplate;

    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public OrderDTO saveOrderDTO(OrderDTO orderDTO) {
        OrderDTO savedOrder = saveOrder(orderDTO);
        sendToKafka(savedOrder);

        return savedOrder;
    }

    private OrderDTO saveOrder(OrderDTO orderDTO){
        OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
        orderRepository.save(orderEntity);

        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setCreatedAt(orderEntity.getCreatedAt());

        return orderDTO;
    }

    private void sendToKafka(OrderDTO savedOrder) {
        try {
            String jsonString = objectMapper.writeValueAsString(savedOrder);
            CompletableFuture<SendResult<String, String>>result = kafkaTemplate.send(ORDER_COMPLETE_TOPIC, jsonString);

            result.thenAccept(resultData -> {
                log.info("kafka 응답 결과, offset: {}", resultData.getRecordMetadata().offset());
                log.info("kafka 응답 결과, topic: {}", resultData.getProducerRecord().topic());
                log.info("kafka 응답 결과, recode: {}", resultData.getProducerRecord().value());
            }).exceptionally(ex -> {
                log.info("[PurchaseService] Message failed to send {}", ex);
                return null;
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
