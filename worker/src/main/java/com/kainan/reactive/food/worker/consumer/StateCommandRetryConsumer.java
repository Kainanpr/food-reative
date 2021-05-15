package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateCommandDltProducer;
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
public class StateCommandRetryConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StateCommandRetryConsumer.class);

    private final StateProcessorService stateProcessorService;
    private final StateCommandDltProducer stateCommandDltProducer;
    private final RetryBackoffSpec defaultRetryBackoffSpec;
    private final KafkaReceiver<String, StateCommand> kafkaReceiver;

    public StateCommandRetryConsumer(
            @Value("${kafka.topics.state-command-retry.name}") String topic,
            ReceiverOptions<String, StateCommand> receiverOptions,
            StateProcessorService stateProcessorService,
            StateCommandDltProducer stateCommandDltProducer,
            RetryBackoffSpec defaultRetryBackoffSpec
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.stateProcessorService = stateProcessorService;
        this.stateCommandDltProducer = stateCommandDltProducer;
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
                .concatMap(message -> stateProcessorService.processMessage(message)
                        .doOnError(error -> log.error("An error occurred while processing the message from the topic RETRY: key - {}, errorMessage - {}",
                                message.key(), error.getMessage())
                        )
                        .retryWhen(defaultRetryBackoffSpec)
                        .onErrorResume(ex -> {
                            log.error("All attempts failed: key - {}, errorMessage - {}", message.key(), ex.getMessage());
                            return stateCommandDltProducer.sendMessage(message.key(), message.value()).then(Mono.empty());
                        })
                )
                .subscribe();
    }
}
