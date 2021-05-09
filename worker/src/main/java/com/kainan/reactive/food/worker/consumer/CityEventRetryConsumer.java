package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventDltProducer;
import com.kainan.reactive.food.worker.service.CityProcessService;
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
public class CityEventRetryConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityEventRetryConsumer.class);

    private final CityProcessService cityProcessService;
    private final CityEventDltProducer cityEventDltProducer;
    private final RetryBackoffSpec defaultRetryBackoffSpec;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventRetryConsumer(
            @Value("${kafka.topics.city-event-retry.name}") String topic,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessService cityProcessService,
            CityEventDltProducer cityEventDltProducer,
            RetryBackoffSpec defaultRetryBackoffSpec
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.cityProcessService = cityProcessService;
        this.cityEventDltProducer = cityEventDltProducer;
        this.defaultRetryBackoffSpec = defaultRetryBackoffSpec;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        kafkaReceiver
                .receive()
                .doOnNext(message -> log.info("message consumed - message: {}", message))
                .flatMap(message -> cityProcessService.processMessageInRetry(message)
                        .doOnError(error -> log.error("An error occurred while processing the message from the topic RETRY: key - {}, errorMessage - {}",
                                message.key(), error.getMessage())
                        )
                        .retryWhen(defaultRetryBackoffSpec)
                        .onErrorResume(ex -> {
                            log.error("All attempts failed: key - {}, errorMessage - {}", message.key(), ex.getMessage());
                            return cityEventDltProducer.sendEvent(message.key(), message.value()).thenMany(Mono.empty());
                        })
                        .doOnTerminate(() -> {
                            message.receiverOffset().acknowledge();
                        })
                )
                .subscribe();
    }
}
