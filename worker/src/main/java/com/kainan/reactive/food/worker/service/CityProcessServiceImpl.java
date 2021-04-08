package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventRetryProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.SenderResult;

@Service
@AllArgsConstructor
@Slf4j
public class CityProcessServiceImpl implements CityProcessService {
    private final CityEventRetryProducer cityEventRetryProducer;

    @Override
    public Flux<ReceiverRecord<String, CityEvent>> processMessage(ReceiverRecord<String, CityEvent> message) {
        return Flux.error(new UnsupportedOperationException("WIP"));
    }

    @Override
    public Flux<SenderResult<String>> sendToRetryTopic(ReceiverRecord<String, CityEvent> message) {
        return cityEventRetryProducer.sendEvent(message.key(), message.value());
    }
}
