package com.kainan.reactive.food.worker.service;

import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.business.domain.service.StateService;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateEventProducer;
import com.kainan.reactive.food.worker.mapper.StateMapperWorker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class StateProcessorServiceImpl implements StateProcessorService {
    private final StateService stateService;
    private final StateEventProducer stateEventProducer;
    private final StateMapperWorker stateMapperWorker;

    public StateProcessorServiceImpl(
            StateService stateService,
            StateEventProducer stateEventProducer,
            StateMapperWorker stateMapperWorker
    ) {
        this.stateService = stateService;
        this.stateEventProducer = stateEventProducer;
        this.stateMapperWorker = stateMapperWorker;
    }

    @Override
    public Mono<StateRead> processMessage(ReceiverRecord<String, StateCommand> message) {
        return stateService.upsert(stateMapperWorker.toStateEntity(message.value()))
                .flatMap(stateRead -> {
                    final var stateEvent = stateMapperWorker.toStateEvent(stateRead);
                    return stateEventProducer.sendMessage(stateEvent.id().toString(), stateEvent)
                            .then(Mono.just(stateRead));
                });
    }
}
