package com.kainan.reactive.food.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class ReactiveKafkaInfraConfig {

    @Bean
    public <V> KafkaSender<String, V> kafkaCityEventSender(
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        return KafkaSender.create(createSenderOptions(properties, objectMapper));
    }

    private <V> SenderOptions<String, V> createSenderOptions(
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        return SenderOptions.<String, V>create(properties.buildProducerProperties()).maxInFlight(1024)
                .withKeySerializer(new StringSerializer())
                .withValueSerializer(new JsonSerializer<>(objectMapper));
    }
}
