package com.kainan.reactive.food.infrastructure.mapper;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import com.kainan.reactive.food.infrastructure.kafka.event.StateEvent;
import org.springframework.stereotype.Service;

@Service
public class CityMapperInfraImpl implements CityMapperInfra {

    @Override
    public CityRead toCityRead(CityEntity cityEntity, StateEntity stateEntity) {
        return new CityRead(
                cityEntity.id(),
                cityEntity.name(),
                new StateRead(
                        stateEntity.id(),
                        stateEntity.name()
                )
        );
    }

    @Override
    public CityEvent toCityEvent(CityRead cityRead) {
        final var stateRead = cityRead.state();
        return new CityEvent(
                cityRead.id(),
                cityRead.name(),
                new StateEvent(
                        stateRead.id(),
                        stateRead.name()
                )
        );
    }
}
