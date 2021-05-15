package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventDltProducer;
import com.kainan.reactive.food.infrastructure.kafka.util.CityEventTopicName;
import com.kainan.reactive.food.worker.service.CityProcessorService;
import org.slf4j.Logger;
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

    private final CityProcessorService cityProcessorService;
    private final CityEventDltProducer cityEventDltProducer;
    private final RetryBackoffSpec defaultRetryBackoffSpec;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    public CityEventRetryConsumer(
            CityEventTopicName topicName,
            ReceiverOptions<String, CityEvent> receiverOptions,
            CityProcessorService cityProcessorService,
            CityEventDltProducer cityEventDltProducer,
            RetryBackoffSpec defaultRetryBackoffSpec
    ) {
        this.kafkaReceiver = KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(topicName.getCityEventRetryTopicName())));
        this.cityProcessorService = cityProcessorService;
        this.cityEventDltProducer = cityEventDltProducer;
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
                .concatMap(message -> cityProcessorService.processMessage(message)
                        .doOnError(error -> log.error("An error occurred while processing the message from the topic RETRY: key - {}, errorMessage - {}",
                                message.key(), error.getMessage())
                        )
                        .retryWhen(defaultRetryBackoffSpec)
                        .onErrorResume(ex -> {
                            log.error("All attempts failed: key - {}, errorMessage - {}", message.key(), ex.getMessage());
                            return cityEventDltProducer.sendMessage(message.key(), message.value()).thenMany(Mono.empty());
                        })
                )
                .subscribe();
    }
}
