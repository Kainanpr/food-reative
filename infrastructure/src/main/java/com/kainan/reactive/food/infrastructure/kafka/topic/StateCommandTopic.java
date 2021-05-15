package com.kainan.reactive.food.infrastructure.kafka.topic;

import com.kainan.reactive.food.infrastructure.kafka.util.StateCommandTopicName;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class StateCommandTopic {
    private final StateCommandTopicName topicName;
    private final int partitions;
    private final int replicas;

    public StateCommandTopic(
            StateCommandTopicName topicName,
            @Value("${kafka.topics.state-command.partitions}") int partitions,
            @Value("${kafka.topics.state-command.replicas}") int replicas
    ) {
        this.topicName = topicName;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newStateCommandTopic() {
        return TopicBuilder.name(topicName.getStateCommandNameTopic())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic newStateCommandRetryTopic() {
        return TopicBuilder.name(topicName.getStateCommandRetryTopicName())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic newStateCommandDltTopic() {
        return TopicBuilder.name(topicName.getStateCommandDltTopicName())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
