package com.example.ordersystemproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${kafka.prev-topic}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendWithCallback(String message){
        String key = "f904221a-1227-44d7-9e6e-868387fdce71_diskio_vda14";
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(TOPIC_NAME, key, message);

        result.thenAccept(resultData -> {
            // 성공 callback
            log.info("Message sent successfully to topic = {}", resultData.getRecordMetadata().topic());
            log.info("Message sent successfully to topic = {}", resultData.getProducerRecord().value());
        }).exceptionally(ex -> {
            // 실패 callback
            log.info("Message failed to send: {}", ex);
            return null;
        });

    }
}