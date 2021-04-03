package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityRepository {
    Flux<CityEntity> getAll();

    Mono<CityEntity> insert(CityEntity cityEntity);
}
