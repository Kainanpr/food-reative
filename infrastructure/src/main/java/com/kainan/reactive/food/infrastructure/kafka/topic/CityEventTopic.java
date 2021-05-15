package com.kainan.reactive.food.infrastructure.kafka.topic;

import com.kainan.reactive.food.infrastructure.kafka.util.CityEventTopicName;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CityEventTopic {
    private final CityEventTopicName topicName;
    private final int partitions;
    private final int replicas;

    public CityEventTopic(
            CityEventTopicName topicName,
            @Value("${kafka.topics.city-event.partitions}") int partitions,
            @Value("${kafka.topics.city-event.replicas}") int replicas
    ) {
        this.topicName = topicName;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public NewTopic newCityEventTopic() {
        return TopicBuilder.name(topicName.getCityEventNameTopic())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic newCityEventRetryTopic() {
        return TopicBuilder.name(topicName.getCityEventRetryTopicName())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic newCityEventDltTopic() {
        return TopicBuilder.name(topicName.getCityEventDltTopicName())
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
