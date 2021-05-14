package com.kainan.reactive.food.worker.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;

public interface StateMapperWorker {
    StateEntity toStateEntity(StateEvent stateEvent);
}
