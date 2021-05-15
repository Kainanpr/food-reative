package com.kainan.reactive.food.infrastructure.kafka.topic;

import com.kainan.reactive.food.infrastructure.kafka.util.StateEventTopicName;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class StateEventTopic {
    private final StateEventTopicName topicName;
    private final int partitions;
    private final int replicas;

    public StateEventTopic(
            StateEventTopicName topicName,
            @Value("${kafka.topics.state-event.partitions}") int partitions,
            @Value("${kafka.topics.state-event.replicas}") int replicas
    ) {
        this.topicName = topicName;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newStateEventTopic() {
        return TopicBuilder.name(topicName.getStateEventNameTopic())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
