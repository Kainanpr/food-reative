package com.kainan.reactive.food.worker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
@Slf4j
public class ReactiveKafkaWorkerConfig {

    @Bean
    public <V> ReceiverOptions<String, V> createReceiverOptions(
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        final JsonDeserializer<V> jsonDeserializer = new JsonDeserializer<>(objectMapper);
        jsonDeserializer.addTrustedPackages("*");

        return ReceiverOptions.<String, V>create(properties.buildConsumerProperties())
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(jsonDeserializer)
                .addAssignListener(assignments -> log.info(assignments.toString()));
    }
}
