package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateEventDltProducer;
import com.kainan.reactive.food.worker.service.StateProcessorService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.util.retry.RetryBackoffSpec;

import java.util.Collections;

@Component
public class StateEventRetryConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StateEventRetryConsumer.class);

    private final StateProcessorService stateProcessorService;
    private final StateEventDltProducer stateEventDltProducer;
    private final RetryBackoffSpec defaultRetryBackoffSpec;
    private final KafkaReceiver<String, StateEvent> kafkaReceiver;

    public StateEventRetryConsumer(
            @Value("${kafka.topics.state-event-retry.name}") String topic,
            ReceiverOptions<String, StateEvent> receiverOptions,
            StateProcessorService stateProcessorService,
            StateEventDltProducer stateEventDltProducer,
            RetryBackoffSpec defaultRetryBackoffSpec
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.stateProcessorService = stateProcessorService;
        this.stateEventDltProducer = stateEventDltProducer;
        this.defaultRetryBackoffSpec = defaultRetryBackoffSpec;
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
                        .doOnError(error -> log.error("An error occurred while processing the message from the topic RETRY: key - {}, errorMessage - {}",
                                message.key(), error.getMessage())
                        )
                        .retryWhen(defaultRetryBackoffSpec)
                        .onErrorResume(ex -> {
                            log.error("All attempts failed: key - {}, errorMessage - {}", message.key(), ex.getMessage());
                            return stateEventDltProducer.sendEvent(message.key(), message.value()).then(Mono.empty());
                        })
                )
                .subscribe();
    }
}
