package com.example.ordersystemconsumer.service;

import com.example.ordersystemconsumer.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final String TOPIC_NAME_FOR_ORDER = "order.complete.v1";
    private static final String TOPIC_NAME_FOR_AGGREGATED = "aggregated.complete.v1";
    private static final String GROUP_ID1 = "shipment.group.v1";
    private static final String GROUP_ID2 = "aggregated.group.v1";

    private final ShipmentService shipmentService;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



    @KafkaListener(topics = TOPIC_NAME_FOR_ORDER, groupId = GROUP_ID1)
    public void recordListener(String jsonMessage){
        try {
            OrderDTO orderDTO = objectMapper.readValue(jsonMessage, OrderDTO.class);
            log.info("consuming한 데이터: {}", orderDTO);
            shipmentService.saveOrder(orderDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = TOPIC_NAME_FOR_AGGREGATED, groupId = GROUP_ID2)
    public void aggregatedRecordListener(String jsonMessage){
        try {
            String aggregatedValue = objectMapper.readValue(jsonMessage, String.class);
            log.info("Kafka Streams를 통해 aggregation한 데이터: {}", aggregatedValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
