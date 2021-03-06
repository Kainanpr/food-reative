package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import com.kainan.reactive.food.business.domain.service.CityService;
import com.kainan.reactive.food.infrastructure.kafka.publisher.CityEventProducer;
import com.kainan.reactive.food.infrastructure.mapper.CityMapperInfra;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityServiceImpl implements CityService {
    private final CityEventProducer cityEventProducer;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapperInfra cityMapperInfra;

    public CityServiceImpl(
            CityEventProducer cityEventProducer,
            CityRepository cityRepository,
            StateRepository stateRepository,
            CityMapperInfra cityMapperInfra
    ) {
        this.cityEventProducer = cityEventProducer;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.cityMapperInfra = cityMapperInfra;
    }

    @Override
    public Mono<CityRead> getById(Long id) {
        return cityRepository.getById(id)
                .flatMap(this::combineWithState);
    }

    private Mono<CityRead> combineWithState(CityEntity cityEntity) {
        return stateRepository.getById(cityEntity.stateId()).map(
                stateEntity -> cityMapperInfra.toCityRead(cityEntity, stateEntity));
    }

    @Override
    public Flux<CityRead> getAll() {
        return cityRepository.getAll()
                .flatMap(this::combineWithState);
    }

    @Override
    @Transactional
    public Mono<CityRead> insert(CityEntity cityEntity) {
        return cityRepository.insert(cityEntity)
                .flatMap(this::combineWithState)
                .flatMap(cityRead -> cityEventProducer
                        .sendMessage(cityRead.id().toString(), cityMapperInfra.toCityEvent(cityRead))
                        .then(Mono.just(cityRead)));
    }

    @Override
    @Transactional
    public Mono<CityRead> update(CityEntity cityEntity) {
        return cityRepository.update(cityEntity)
                .flatMap(this::combineWithState)
                .flatMap(cityRead -> cityEventProducer
                        .sendMessage(cityRead.id().toString(), cityMapperInfra.toCityEvent(cityRead))
                        .then(Mono.just(cityRead)));
    }
}
