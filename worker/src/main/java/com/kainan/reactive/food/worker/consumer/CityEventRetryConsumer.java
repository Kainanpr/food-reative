package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventDltProducer;
import com.kainan.reactive.food.worker.service.CityProcessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class CityEventRetryConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityEventRetryConsumer.class);

    private final CityProcessService cityProcessService;
    private final CityEventDltProducer cityEventDltProducer;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventRetryConsumer(
            @Value("${kafka.topics.city-event-retry.name}") String topic,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessService cityProcessService,
            CityEventDltProducer cityEventDltProducer
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.cityProcessService = cityProcessService;
        this.cityEventDltProducer = cityEventDltProducer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        final var backoff = Retry.backoff(3, Duration.ofSeconds(2L));
        kafkaReceiver
                .receive()
                .doOnNext(message -> log.info("message consumed - message: {}", message))
                .flatMap(message -> cityProcessService.processMessage(message)
                        .doOnError(error -> log.error("An error occurred while consuming the message from the topic RETRY: {}", error.getMessage()))
                        .retryWhen(backoff)
                        .onErrorResume(ex -> {
                            log.error("All attempts failed: {}", ex.getMessage());
                            message.receiverOffset().acknowledge();
                            return cityEventDltProducer.sendEvent(message.key(), message.value()).thenMany(Mono.empty());
                        })
                )
                .subscribe();
    }
}
