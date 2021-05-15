package com.kainan.reactive.food.worker.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;

public interface StateMapperWorker {
    StateEntity toStateEntity(StateCommand stateCommand);

    StateEvent toStateEvent(StateRead stateRead);
}
