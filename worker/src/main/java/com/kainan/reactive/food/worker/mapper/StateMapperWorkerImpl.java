package com.kainan.reactive.food.worker.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import org.springframework.stereotype.Component;

@Component
public class StateMapperWorkerImpl implements StateMapperWorker {
    @Override
    public StateEntity toStateEntity(StateEvent stateEvent) {
        return new StateEntity(
                stateEvent.id(),
                stateEvent.name()
        );
    }
}
