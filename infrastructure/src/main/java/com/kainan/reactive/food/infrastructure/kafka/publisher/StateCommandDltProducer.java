package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.util.StateCommandTopicName;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
public class StateCommandDltProducer extends CommonProducer<StateCommand> {
    public StateCommandDltProducer(
            StateCommandTopicName topicName,
            KafkaSender<String, StateCommand> kafkaSender
    ) {
        super(topicName.getStateCommandDltTopicName(), kafkaSender);
    }
}
