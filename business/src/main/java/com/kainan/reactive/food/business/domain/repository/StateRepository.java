package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<StateEntity> getById(Long id);

    Flux<StateEntity> getAll();

    Mono<StateEntity> insert(StateEntity stateEntity);

    Mono<StateEntity> update(StateEntity stateEntity);
}
