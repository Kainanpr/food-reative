package com.kainan.reactive.food.business.domain.service;

import com.kainan.reactive.food.business.domain.model.City;
import reactor.core.publisher.Flux;

public interface CityService {
    Flux<City> getAll();
}
