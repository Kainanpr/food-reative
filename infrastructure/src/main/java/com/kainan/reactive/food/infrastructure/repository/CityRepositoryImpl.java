package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.model.City;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class CityRepositoryImpl implements CityRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<City> getAll() {
        return template.select(City.class).all();
    }

    @Override
    public Mono<City> insert(City city) {
        return template.insert(city);
    }
}
