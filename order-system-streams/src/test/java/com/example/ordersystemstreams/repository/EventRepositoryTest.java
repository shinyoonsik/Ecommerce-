package com.example.ordersystemstreams.repository;

import com.example.ordersystemstreams.entity.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 저장 테스트")
    void test(){
        Event event = Event.builder()
                .name("mem체크")
                .rule1("80")
                .rule2("3")
                .build();

        Event result = eventRepository.save(event);

        assertThat(result.getName()).isEqualTo("mem체크");
    }

    @Test
    @DisplayName("이벤트 조회 테스트")
    public void test2(){
        List<Event> result = eventRepository.findAll();

        for (Event event : result) {
            System.out.println("event = " + event);
        }
    }
}