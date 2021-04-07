package com.kainan.reactive.food.infrastructure.mapper;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapperInfra {

    @Mapping(target = "id", source = "cityEntity.id")
    @Mapping(target = "name", source = "cityEntity.name")
    @Mapping(target = "state", source = "stateEntity")
    CityRead toCityRead(CityEntity cityEntity, StateEntity stateEntity);

    CityEvent toCityEvent(CityRead cityRead);
}
