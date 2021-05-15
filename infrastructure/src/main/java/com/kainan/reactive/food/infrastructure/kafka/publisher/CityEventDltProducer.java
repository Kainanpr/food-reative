package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.util.CityEventTopicName;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class CityEventDltProducer extends CommonProducer<CityEvent> {
    public CityEventDltProducer(
            CityEventTopicName topicName,
            KafkaSender<String, CityEvent> kafkaSender
    ) {
        super(topicName.getCityEventDltTopicName(), kafkaSender);
    }
}
