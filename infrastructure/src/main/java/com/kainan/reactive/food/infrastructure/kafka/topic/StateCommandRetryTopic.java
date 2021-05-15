package com.kainan.reactive.food.infrastructure.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class StateCommandRetryTopic {
    private final String topic;
    private final int partitions;
    private final int replicas;

    public StateCommandRetryTopic(
            @Value("${kafka.topics.state-command-retry.name}") String topic,
            @Value("${kafka.topics.state-command-retry.partitions}") int partitions,
            @Value("${kafka.topics.state-command-retry.replicas}") int replicas
    ) {
        this.topic = topic;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newStateCommandRetryTopic() {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
