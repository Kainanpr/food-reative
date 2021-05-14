package com.kainan.reactive.food.infrastructure.mapper;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;

public interface StateMapperInfra {
    StateRead toStateRead(StateEntity stateEntity);
}
