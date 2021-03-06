package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

public interface CityProcessorService {
    Flux<ReceiverRecord<String, CityEvent>> processMessage(ReceiverRecord<String, CityEvent> message);
}
