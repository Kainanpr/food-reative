package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventRetryProducer;
import com.kainan.reactive.food.worker.service.CityProcessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
public class CityEventConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityEventConsumer.class);

    private final CityProcessService cityProcessService;
    private final CityEventRetryProducer cityEventRetryProducer;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventConsumer(
            @Value("${kafka.topics.city-event.name}") String topic,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessService cityProcessService,
            CityEventRetryProducer cityEventRetryProducer
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topic)));
        this.cityProcessService = cityProcessService;
        this.cityEventRetryProducer = cityEventRetryProducer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        kafkaReceiver
                .receive()
                .doOnNext(message -> {
                    log.info("message consumed - message: {}", message);
                    message.receiverOffset().acknowledge();
                })
                .flatMap(message -> cityProcessService.processMessage(message)
                        .onErrorResume(error -> {
                            log.error("An error occurred while consuming the message: {}", error.getMessage());
                            return cityEventRetryProducer.sendEvent(message.key(), message.value()).thenMany(Mono.empty());
                        })
                )
                .subscribe();
    }
}
