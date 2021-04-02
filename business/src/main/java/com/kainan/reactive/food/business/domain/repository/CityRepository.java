package com.kainan.reactive.food.business.domain.repository;

import com.kainan.reactive.food.business.domain.model.City;
import reactor.core.publisher.Flux;

public interface CityRepository {
    Flux<City> getAll();
}
