package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import com.kainan.reactive.food.infrastructure.kafka.util.StateEventTopicName;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class StateEventProducer extends CommonProducer<StateEvent> {
    public StateEventProducer(
            StateEventTopicName topicName,
            KafkaSender<String, StateEvent> kafkaSender
    ) {
        super(topicName.getStateEventNameTopic(), kafkaSender);
    }
}
