package com.kainan.reactive.food.infrastructure.kafka.publisher;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

public abstract class CommonProducer<V> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonProducer.class);

    private final String topic;
    private final KafkaSender<String, V> kafkaSender;

    protected CommonProducer(
            String topic,
            KafkaSender<String, V> kafkaSender
    ) {
        this.topic = topic;
        this.kafkaSender = kafkaSender;
    }

    public Flux<SenderResult<String>> sendMessage(String key, V value) {
        return kafkaSender.send(Mono.fromCallable(() -> {
            log.info("publishing message - topic: {}, key: {}, value: {}", topic, key, value);
            final var producerRecord = new ProducerRecord<>(topic, key, value);
            return SenderRecord.create(producerRecord, topic);
        }));
    }
}
