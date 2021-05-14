package com.kainan.reactive.food.infrastructure.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import org.springframework.stereotype.Component;

@Component
public class StateMapperInfraImpl implements StateMapperInfra {

    @Override
    public StateRead toStateRead(StateEntity stateEntity) {
        return new StateRead(
                stateEntity.id(),
                stateEntity.name()
        );
    }
}
