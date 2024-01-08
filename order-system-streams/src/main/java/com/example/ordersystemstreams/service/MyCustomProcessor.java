package com.example.ordersystemstreams.service;

import com.example.ordersystemstreams.entity.Event;
import com.example.ordersystemstreams.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MyCustomProcessor implements Processor<String, String, String, String> {

    private final EventRepository eventRepository;

    private KeyValueStore<String, String> kvStore;
    private ProcessorContext context;
    private List<Event> eventList; // 이벤트 규칙정보


    // ProcessorContext: 스트림 처리에 필요한 다양한 메타데이터와 상태 저장소, 그리고 스트림 처리 과정에 대한 제어정보를 담은 객체
    @Override
    public void init(ProcessorContext context) {
        Processor.super.init(context);

        this.context = context;
        this.eventList = new ArrayList<>();
        this.kvStore = context.getStateStore("event_kvs");
    }

    @Override
    public void process(Record record) {
        if(eventList.isEmpty()){
            eventList = eventRepository.findAll();
        }


        for (Event event : eventList) {
            System.out.println("Record Key: " + record.key() + " | Record Value: " + record.value());
            System.out.println("event = " + event);
        }

        context.forward(record);
    }

    @Override
    public void close() {
        Processor.super.close();
    }
}
