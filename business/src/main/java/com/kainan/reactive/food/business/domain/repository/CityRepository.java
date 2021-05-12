package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityRepository {
    Mono<CityEntity> getById(Long id);

    Flux<CityEntity> getAll();

    Mono<CityEntity> insert(CityEntity cityEntity);

    Mono<CityEntity> update(CityEntity cityEntity);
}
