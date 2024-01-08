package com.example.ordersystemproducer.controller;

import com.example.ordersystemproducer.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class ProducerTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @Test
    void test() throws InterruptedException {
        String input = getData();
        String errorInput = getErrorData();

        for (int i = 0; i < 5; i++) {
            kafkaProducerService.sendWithCallback(input);
        }
    }


    String getData() {
        return "{\n" +
                "\t\"timestamp\": 1703408620,\n" +
                "\t\"user_agent\": {\n" +
                "\t\t\"original\": \"Telegraf/1.28.5 Go/1.21.4\"\n" +
                "\t},\n" +
                "\t\"http\": {\n" +
                "\t\t\"version\": \"HTTP/1.1\",\n" +
                "\t\t\"request\": {\n" +
                "\t\t\t\"body\": {\n" +
                "\t\t\t\t\"bytes\": \"353\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"mime_type\": \"text/plain; charset=utf-8\"\n" +
                "\t\t},\n" +
                "\t\t\"method\": \"POST\"\n" +
                "\t},\n" +
                "\t\"host\": {\n" +
                "\t\t\"ip\": \"10.10.10.92\"\n" +
                "\t},\n" +
                "\t\"delta_key\": \"f904221a-1227-44d7-9e6e-868387fdce71_diskio_vda14\",\n" +
                "\t\"url\": {\n" +
                "\t\t\"path\": \"/\",\n" +
                "\t\t\"port\": 5042,\n" +
                "\t\t\"domain\": \"10.10.10.237\"\n" +
                "\t},\n" +
                "\t\"identifier\": \"f904221a-1227-44d7-9e6e-868387fdce71\",\n" +
                "\t\"name\": \"diskio\",\n" +
                "\t\"fields\": {\n" +
                "\t\t\"writes\": 0,\n" +
                "\t\t\"merged_reads\": 0,\n" +
                "\t\t\"read_time\": 226,\n" +
                "\t\t\"weighted_io_time\": 0,\n" +
                "\t\t\"io_time\": 1012,\n" +
                "\t\t\"write_time\": 0,\n" +
                "\t\t\"iops_in_progress\": 0,\n" +
                "\t\t\"reads\": 389,\n" +
                "\t\t\"read_bytes\": 7374848,\n" +
                "\t\t\"write_bytes\": 0,\n" +
                "\t\t\"merged_writes\": 0\n" +
                "\t},\n" +
                "\t\"@version\": \"1\",\n" +
                "\t\"@timestamp\": \"2023-12-24T09:03:44.579617527Z\",\n" +
                "\t\"tag_info\": {\n" +
                "\t\t\"name\": \"vda14\",\n" +
                "\t\t\"host\": \"backend-server\",\n" +
                "\t\t\"source\": \"diskio\"\n" +
                "\t},\n" +
                "\t\"source\": \"http_5042\"\n" +
                "}";
    }

    String getErrorData() {
        return "{\n" +
                "\t\"timestamp\": 1703408620,\n" +
                "\t\"user_agent\": {\n" +
                "\t\t\"original\": \"Telegraf/1.28.5 Go/1.21.4\"\n" +
                "\t},\n" +
                "\t\"http\": {\n" +
                "\t\t\"version\": \"HTTP/1.1\",\n" +
                "\t\t\"request\": {\n" +
                "\t\t\t\"body\": {\n" +
                "\t\t\t\t\"bytes\": \"353\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"mime_type\": \"text/plain; charset=utf-8\"\n" +
                "\t\t},\n" +
                "\t\t\"method\": \"POST\"\n" +
                "\t},\n" +
                "\t\"host\": {\n" +
                "\t\t\"ip\": \"10.10.10.92\"\n" +
                "\t},\n" +
                "\t\"delta_key\": \"f904221a-1227-44d7-9e6e-868387fdce71_diskio_vda14\",\n" +
                "\t\"url\": {\n" +
                "\t\t\"path\": \"/\",\n" +
                "\t\t\"port\": 5042,\n" +
                "\t\t\"domain\": \"10.10.10.237\"\n" +
                "\t},\n" +
                "\t\"identifier\": \"f904221a-1227-44d7-9e6e-868387fdce71\",\n" +
                "\t\"name\": \"diskio\",\n" +
                "\t\"fields\": {\n" +
                "\t\t\"writes\": 0,\n" +
                "\t\t\"merged_reads\": 0,\n" +
                "\t\t\"read_time\": 226,\n" +
                "\t\t\"weighted_io_time\": 0,\n" +
                "\t\t\"io_time\": 1012,\n" +
                "\t\t\"write_time\": 0,\n" +
                "\t\t\"iops_in_progress\": 0,\n" +
                "\t\t\"reads\": 389,\n" +
                "\t\t\"read_bytes\": 7374848,\n" +
                "\t\t\"write_bytes\": 0,\n" +
                "\t\t\"merged_writes\": 0\n" +
                "\t},\n" +
                "\t\"@version\": \"1\",\n" +
                "\t\"@timestamp\": \"2023-12-24T09:03:44.579617527Z\",\n" +
                "\t\"tag_info\": {\n" +
                "\t\t\"name\": \"vda14\",\n" +
                "\t\t\"host\": \"backend-server\",\n" +
                "\t\t\"source\": \"diskio\"\n" +
                "\t}, mystring\n" +
                "\t\"source\": \"http_5042\"\n" +
                "}}}}";
    }

}
