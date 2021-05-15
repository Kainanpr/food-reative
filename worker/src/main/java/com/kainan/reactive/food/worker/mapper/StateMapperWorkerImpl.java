package com.kainan.reactive.food.worker.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import org.springframework.stereotype.Component;

@Component
public class StateMapperWorkerImpl implements StateMapperWorker {
    @Override
    public StateEntity toStateEntity(StateCommand stateCommand) {
        return new StateEntity(
                stateCommand.id(),
                stateCommand.name()
        );
    }

    @Override
    public StateEvent toStateEvent(StateRead stateRead) {
        return new StateEvent(
                stateRead.id(),
                stateRead.name()
        );
    }
}
