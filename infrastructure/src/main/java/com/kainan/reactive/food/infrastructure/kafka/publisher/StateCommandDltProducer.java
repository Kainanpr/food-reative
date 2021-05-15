package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class StateCommandDltProducer extends CommonProducer<StateCommand> {
    public StateCommandDltProducer(
            @Value("${kafka.topics.state-command-dlt.name}") String topic,
            KafkaSender<String, StateCommand> kafkaSender
    ) {
        super(topic, kafkaSender);
    }
}
