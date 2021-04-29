package com.kainan.reactive.food.worker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
public class ReactiveKafkaWorkerConfig {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReactiveKafkaWorkerConfig.class);

    @Bean
    public KafkaReceiver<String, CityEvent> kafkaCityEventReceiver(
            @Value("${kafka.topics.city-event.name}") String topic,
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        return KafkaReceiver.create(createReceiverOptions(topic, properties, objectMapper));
    }

    private <V> ReceiverOptions<String, V> createReceiverOptions(
            String topic,
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        final JsonDeserializer<V> jsonDeserializer = new JsonDeserializer<>(objectMapper);
        jsonDeserializer.addTrustedPackages("*");

        return ReceiverOptions.<String, V>create(properties.buildConsumerProperties())
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(jsonDeserializer)
                .addAssignListener(assignments -> log.info(assignments.toString()))
                .subscription(Collections.singleton(topic));
    }
}
