package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class StateCommandProducer extends CommonProducer<StateCommand> {
    public StateCommandProducer(
            @Value("${kafka.topics.state-command.name}") String topic,
            KafkaSender<String, StateCommand> kafkaSender
    ) {
        super(topic, kafkaSender);
    }
}
