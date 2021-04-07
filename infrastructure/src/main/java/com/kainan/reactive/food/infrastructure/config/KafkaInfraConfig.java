package com.kainan.reactive.food.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaInfraConfig {

    @Bean
    public <V> ProducerFactory<String, V> kafkaProducerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {
        return new DefaultKafkaProducerFactory<>(
                properties.buildProducerProperties(),
                new StringSerializer(), new JsonSerializer<>(objectMapper)
        );
    }
}
