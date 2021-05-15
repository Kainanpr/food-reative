package com.kainan.reactive.food.infrastructure.kafka.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StateCommandTopicName {
    private final String topic;

    public StateCommandTopicName(
            @Value("${kafka.topics.state-command.name-prefix}") String topic
    ) {
        this.topic = topic;
    }

    public String getStateCommandNameTopic() {
        return topic;
    }

    public String getStateCommandRetryTopicName() {
        return topic + ".RETRY";
    }

    public String getStateCommandDltTopicName() {
        return topic + ".DLT";
    }
}
