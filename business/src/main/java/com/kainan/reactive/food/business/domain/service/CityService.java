package com.kainan.reactive.food.business.domain.service;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {
    Mono<CityRead> getById(Long id);

    Flux<CityRead> getAll();

    Mono<CityRead> insert(CityEntity cityEntity);

    Mono<CityRead> update(CityEntity cityEntity);
}
