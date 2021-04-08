package com.kainan.reactive.food.worker.consumer;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.worker.service.CityProcessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Configuration
@AllArgsConstructor
@Slf4j
public class CityEventConsumer {
    private final CityProcessService cityProcessService;
    private final KafkaReceiver<String, CityEvent> kafkaReceiver;

    @EventListener(ApplicationReadyEvent.class)
    public void consumer() {
        kafkaReceiver
                .receive()
                .doOnNext(message -> {
                    log.info("message consumed - message: {}", message);
                    message.receiverOffset().acknowledge();
                })
                .flatMap((message) ->
                        cityProcessService.processMessage(message)
                                .onErrorResume((error) -> {
                                    error.printStackTrace();
                                    log.info("An error occurred while consuming the message: {}", message);
                                    return cityProcessService.sendToRetryTopic(message).then(Mono.just(message));
                                }))
                .subscribe();
    }
}
