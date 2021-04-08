package com.kainan.reactive.food.infrastructure.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CityEventDltTopic {
    private final String topic;
    private final int partitions;
    private final int replicas;

    public CityEventDltTopic(
            @Value("${kafka.topics.city-event-dlt.name}") String topic,
            @Value("${kafka.topics.city-event-dlt.partitions}") int partitions,
            @Value("${kafka.topics.city-event-dlt.replicas}") int replicas
    ) {
        this.topic = topic;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newCityEventDltTopic() {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
