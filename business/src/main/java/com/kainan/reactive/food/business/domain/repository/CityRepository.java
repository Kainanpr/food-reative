package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityRepository {
    Flux<City> getAll();

    Mono<City> insert(City city);
}
