package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.business.domain.service.StateService;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import com.kainan.reactive.food.worker.mapper.StateMapperWorker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class StateProcessorServiceImpl implements StateProcessorService {
    private final StateService stateService;
    private final StateMapperWorker stateMapperWorker;

    public StateProcessorServiceImpl(StateService stateService, StateMapperWorker stateMapperWorker) {
        this.stateService = stateService;
        this.stateMapperWorker = stateMapperWorker;
    }

    @Override
    public Mono<StateRead> processMessage(ReceiverRecord<String, StateEvent> message) {
        return stateService.upsert(stateMapperWorker.toStateEntity(message.value()));
    }
}
