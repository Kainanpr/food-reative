package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.worker.service.CityProcessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class CityEventRetryConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityEventRetryConsumer.class);

    private final CityProcessService cityProcessService;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventRetryConsumer(
            @Value("${kafka.topics.city-event-retry.name}") String topic,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessService cityProcessService
    ) {
        this.cityProcessService = cityProcessService;
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
    }

    // @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        final var backoff = Retry.backoff(5, Duration.ofSeconds(3L))
                .onRetryExhaustedThrow((v1, v2) -> {
                    log.info("All attempts failed");
                    return v2.failure();
                });
        kafkaReceiver
                .receive()
                .doOnNext(message -> {
                    log.info("message consumed - message: {}", message);
                })
                .flatMap((message) ->
                        cityProcessService.processMessage(message)
                                .doOnError((error) -> {
                                    error.printStackTrace();
                                    log.info("An error occurred while consuming the message from the topic RETRY: {}", message);
                                })
                )
                .retryWhen(backoff)
                .subscribe();
    }
}
