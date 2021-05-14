package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class StateEventDltProducer extends EventCommonProducer<StateEvent> {
    public StateEventDltProducer(
            @Value("${kafka.topics.state-event-dlt.name}") String topic,
            KafkaSender<String, StateEvent> kafkaSender
    ) {
        super(topic, kafkaSender);
    }
}
