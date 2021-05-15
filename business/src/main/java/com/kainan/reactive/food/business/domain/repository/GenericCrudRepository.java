package com.kainan.reactive.food.business.domain.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericCrudRepository<T, ID> {
    Mono<T> getById(ID id);

    Flux<T> getAll();

    Mono<T> insert(T entity);

    Mono<T> update(T entity);
}
