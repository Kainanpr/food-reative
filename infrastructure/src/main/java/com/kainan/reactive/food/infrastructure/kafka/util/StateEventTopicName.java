package com.kainan.reactive.food.infrastructure.kafka.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StateEventTopicName {
    private final String topic;

    public StateEventTopicName(
            @Value("${kafka.topics.state-event.name-prefix}") String topic
    ) {
        this.topic = topic;
    }

    public String getStateEventNameTopic() {
        return topic;
    }
}
