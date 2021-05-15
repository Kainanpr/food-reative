package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateCommandRetryProducer;
import com.kainan.reactive.food.worker.service.StateProcessorService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Component
public class StateCommandConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StateCommandConsumer.class);

    private final StateProcessorService stateProcessorService;
    private final StateCommandRetryProducer stateCommandRetryProducer;
    private final KafkaReceiver<String, StateCommand> kafkaReceiver;

    public StateCommandConsumer(
            @Value("${kafka.topics.state-command.name}") String topic,
            ReceiverOptions<String, StateCommand> receiverOptions,
            StateProcessorService stateProcessorService,
            StateCommandRetryProducer stateCommandRetryProducer
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.stateProcessorService = stateProcessorService;
        this.stateCommandRetryProducer = stateCommandRetryProducer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        kafkaReceiver
                .receive()
                .doOnNext(message -> {
                    log.info("message consumed - message: {}", message);
                    message.receiverOffset().acknowledge();
                })
                .flatMap(message -> stateProcessorService.processMessage(message)
                        .onErrorResume(error -> {
                            log.error("An error occurred while processing the message: {}", error.getMessage());
                            return stateCommandRetryProducer.sendMessage(message.key(), message.value()).then(Mono.empty());
                        })
                )
                .subscribe();
    }
}
