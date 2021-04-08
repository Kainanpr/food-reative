package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.SenderResult;

public interface CityProcessService {
    Flux<ReceiverRecord<String, CityEvent>> processMessage(ReceiverRecord<String, CityEvent> message);

    Flux<SenderResult<String>> sendToRetryTopic(ReceiverRecord<String, CityEvent> message);
}
