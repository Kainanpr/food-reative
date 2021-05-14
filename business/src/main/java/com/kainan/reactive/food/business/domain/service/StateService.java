package com.kainan.reactive.food.business.domain.service;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateService {
    Mono<StateRead> getById(Long id);

    Flux<StateRead> getAll();

    Mono<StateRead> insert(StateEntity stateEntity);

    Mono<StateRead> update(StateEntity stateEntity);

    Mono<StateRead> upsert(StateEntity stateEntity);
}
