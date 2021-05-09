package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class CityProcessServiceImpl implements CityProcessService {

    @Override
    public Flux<ReceiverRecord<String, CityEvent>> processMessage(ReceiverRecord<String, CityEvent> message) {
        return Flux.error(new UnsupportedOperationException("WIP"));
    }
}
