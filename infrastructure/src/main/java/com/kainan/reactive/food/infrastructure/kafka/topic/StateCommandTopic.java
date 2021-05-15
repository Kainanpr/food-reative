package com.kainan.reactive.food.infrastructure.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class StateCommandTopic {
    private final String topic;
    private final int partitions;
    private final int replicas;

    public StateCommandTopic(
            @Value("${kafka.topics.state-command.name}") String topic,
            @Value("${kafka.topics.state-command.partitions}") int partitions,
            @Value("${kafka.topics.state-command.replicas}") int replicas
    ) {
        this.topic = topic;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newStateCommandTopic() {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}