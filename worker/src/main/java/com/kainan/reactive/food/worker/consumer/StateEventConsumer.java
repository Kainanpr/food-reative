package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateEventRetryProducer;
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
public class StateEventConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StateEventConsumer.class);

    private final StateProcessorService stateProcessorService;
    private final StateEventRetryProducer stateEventRetryProducer;
    private final KafkaReceiver<String, StateEvent> kafkaReceiver;

    public StateEventConsumer(
            @Value("${kafka.topics.state-event.name}") String topic,
            ReceiverOptions<String, StateEvent> receiverOptions,
            StateProcessorService stateProcessorService,
            StateEventRetryProducer stateEventRetryProducer
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.stateProcessorService = stateProcessorService;
        this.stateEventRetryProducer = stateEventRetryProducer;
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
                            return stateEventRetryProducer.sendEvent(message.key(), message.value()).then(Mono.empty());
                        })
                )
                .subscribe();
    }
}
