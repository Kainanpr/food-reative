package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CityEventProducer {
    private final String topic;
    private final KafkaTemplate<String, CityEvent> kafkaTemplate;

    public CityEventProducer(
            @Value("${kafka.topics.city-event.name}") String topic,
            KafkaTemplate<String, CityEvent> kafkaTemplate
    ) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String key, CityEvent value) {
        log.info("publishing message - topic: {}, key: {}, value: {}", topic, key, value);
        final ProducerRecord<String, CityEvent> producerRecord = new ProducerRecord<>(topic, key, value);
        kafkaTemplate.send(producerRecord);
    }
}
