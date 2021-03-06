package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

public interface StateProcessorService {
    Mono<StateRead> processMessage(ReceiverRecord<String, StateCommand> message);
}
