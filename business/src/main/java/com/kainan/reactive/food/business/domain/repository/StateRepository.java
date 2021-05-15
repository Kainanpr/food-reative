package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import reactor.core.publisher.Mono;

public interface StateRepository extends GenericCrudRepository<StateEntity, Long> {
    Mono<StateEntity> upsert(StateEntity stateEntity);
}
