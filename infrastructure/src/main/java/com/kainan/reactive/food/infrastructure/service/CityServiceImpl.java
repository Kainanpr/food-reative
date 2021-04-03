package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import com.kainan.reactive.food.business.domain.service.CityService;
import com.kainan.reactive.food.infrastructure.mapper.CityMapperInfra;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapperInfra cityMapperInfra;

    @Override
    public Flux<CityRead> getAll() {
        return cityRepository.getAll()
                .flatMap(this::zipWithState);
    }

    private Mono<CityRead> zipWithState(CityEntity entity) {
        return Mono.zip(
                Mono.just(entity),
                stateRepository.getById(entity.getStateId()),
                cityMapperInfra::toCityRead);
    }

    @Override
    public Mono<CityRead> insert(CityEntity cityEntity) {
        return cityRepository.insert(cityEntity)
                .flatMap(this::zipWithState);
    }
}
