package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Component
@Slf4j
public class CityEventProducer {
    private final String topic;
    private final KafkaSender<String, CityEvent> kafkaSender;

    public CityEventProducer(
            @Value("${kafka.topics.city-event.name}") String topic,
            KafkaSender<String, CityEvent> kafkaSender
    ) {
        this.topic = topic;
        this.kafkaSender = kafkaSender;
    }

    public Flux<SenderResult<String>> sendEvent(String key, CityEvent value) {
        return kafkaSender.send(Mono.fromCallable(() -> {
            log.info("publishing message - topic: {}, key: {}, value: {}", topic, key, value);
            final ProducerRecord<String, CityEvent> producerRecord = new ProducerRecord<>(topic, key, value);
            return SenderRecord.create(producerRecord, topic);
        }));
    }
}
