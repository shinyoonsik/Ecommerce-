package com.example.ordersystemstreams.service;

import com.example.ordersystemstreams.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MyCustomProcessSupplier implements ProcessorSupplier<String, String, String, String> {
    private final EventRepository eventRepository;

    @Override
    public Processor<String, String, String, String> get() {
        return new MyCustomProcessor(eventRepository);
    }

    @Override
    public Set<StoreBuilder<?>> stores() {
        // todo stateStore설정 확인하기: https://kafka.apache.org/36/documentation/streams/developer-guide/processor-api.html#state-stores
        final StoreBuilder<KeyValueStore<String, String>> stateStoreSupplier = Stores
                .keyValueStoreBuilder(
                        Stores.persistentKeyValueStore("event_kvs"),
                        Serdes.String(),
                        Serdes.String()
                );

        return Collections.singleton(stateStoreSupplier);
    }
}
