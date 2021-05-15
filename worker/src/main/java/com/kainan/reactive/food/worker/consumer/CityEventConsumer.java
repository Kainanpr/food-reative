package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventRetryProducer;
import com.kainan.reactive.food.infrastructure.kafka.util.CityEventTopicName;
import com.kainan.reactive.food.worker.service.CityProcessorService;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Component
public class CityEventConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityEventConsumer.class);

    private final CityProcessorService cityProcessorService;
    private final CityEventRetryProducer cityEventRetryProducer;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventConsumer(
            CityEventTopicName topicName,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessorService cityProcessorService,
            CityEventRetryProducer cityEventRetryProducer
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topicName.getCityEventNameTopic())));
        this.cityProcessorService = cityProcessorService;
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
                .flatMap(message -> cityProcessorService.processMessage(message)
                        .onErrorResume(error -> {
                            log.error("An error occurred while processing the message: {}", error.getMessage());
                            return cityEventRetryProducer.sendMessage(message.key(), message.value()).thenMany(Mono.empty());
                        })
                )
                .subscribe();
    }
}
