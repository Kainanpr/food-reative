package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private final R2dbcEntityTemplate template;

    public CityRepositoryImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<CityEntity> getAll() {
        return template.select(CityEntity.class).all();
    }

    @Override
    public Mono<CityEntity> insert(CityEntity cityEntity) {
        return template.insert(cityEntity);
    }
}
