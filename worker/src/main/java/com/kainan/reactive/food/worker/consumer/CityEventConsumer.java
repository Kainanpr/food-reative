package com.kainan.reactive.food.worker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CityEventConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${kafka.topics.city-event.name}",
            properties = "max.poll.interval.ms=${kafka.max-poll-interval-ms}"
    )
    public void consume(ConsumerRecord<String, CityEvent> message) {
        log.info("message consumed - key: {}, value: {}", message.key(), message.value());
    }
}
