package com.kainan.reactive.food.infrastructure.mapper;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;

public interface CityMapperInfra {
    CityRead toCityRead(CityEntity cityEntity, StateEntity stateEntity);

    CityEvent toCityEvent(CityRead cityRead);
}
