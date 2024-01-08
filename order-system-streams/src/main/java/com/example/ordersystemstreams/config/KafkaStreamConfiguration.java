package com.example.ordersystemstreams.config;

import com.example.ordersystemstreams.service.MyCustomProcessSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaStreamConfiguration {
    private final String inputTopic = "order.complete.v1";
//    private final String inputTopic2 = "delta-topic";
    private final String outputTopic = "aggregated.complete.v1";

    private final MyCustomProcessSupplier myCustomProcessSupplier;

    @Value("${kafka.bootstrap.server}")
    private String BOOTSTRAP_SERVER;

    @Bean
    public KafkaStreams kafkaStreams() {
        Topology topology = createTopology();
        Properties props = createProperties();
        KafkaStreams kafkaStreams = createKafkaStreams(topology, props);
        kafkaStreams.start();

        return kafkaStreams;
    }

    private Topology createTopology() {
        Topology topology = new Topology();

        topology.addSource("mySource", inputTopic)
                .addProcessor("myProcessor", myCustomProcessSupplier, "mySource")
                .addSink("mySink", outputTopic, "myProcessor");

        return topology;
    }

    private Properties createProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return props;
    }

    private KafkaStreams createKafkaStreams(Topology topology, Properties props) {
        return new KafkaStreams(topology, props);
    }
}


