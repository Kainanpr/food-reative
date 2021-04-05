package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import com.kainan.reactive.food.business.domain.service.CityService;
import com.kainan.reactive.food.infrastructure.mapper.CityMapperInfra;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                .flatMap(this::combineWithState);
    }

    private Mono<CityRead> combineWithState(CityEntity cityEntity) {
        return stateRepository.getById(cityEntity.getStateId()).map(
                stateEntity -> cityMapperInfra.toCityRead(cityEntity, stateEntity));
    }

    @Override
    @Transactional
    public Mono<CityRead> insert(CityEntity cityEntity) {
        return cityRepository.insert(cityEntity)
                .flatMap(this::combineWithState);
    }
}
