package com.kainan.reactive.food.worker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Configuration
public class ReactiveKafkaWorkerConfig {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReactiveKafkaWorkerConfig.class);

    @Bean
    public RetryBackoffSpec defaultRetryBackoffSpec(
            @Value("${kafka.default-max-attempts}") String defaultMaxAttempts,
            @Value("${kafka.default-back-off-second}") String defaultBackOffSecond
    ) {
        return Retry.backoff(
                Integer.parseInt(defaultMaxAttempts),
                Duration.ofSeconds(Long.parseLong(defaultBackOffSecond))
        );
    }

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
