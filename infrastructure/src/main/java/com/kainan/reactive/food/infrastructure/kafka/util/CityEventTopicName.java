package com.kainan.reactive.food.infrastructure.kafka.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CityEventTopicName {
    private final String topic;

    public CityEventTopicName(
            @Value("${kafka.topics.city-event.name-prefix}") String topic
    ) {
        this.topic = topic;
    }

    public String getCityEventNameTopic() {
        return topic;
    }

    public String getCityEventRetryTopicName() {
        return topic + ".RETRY";
    }

    public String getCityEventDltTopicName() {
        return topic + ".DLT";
    }
}
