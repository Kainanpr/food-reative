package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import com.kainan.reactive.food.business.domain.service.StateService;
import com.kainan.reactive.food.infrastructure.mapper.StateMapperInfra;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;
    private final StateMapperInfra stateMapperInfra;

    public StateServiceImpl(
            StateRepository stateRepository,
            StateMapperInfra stateMapperInfra
    ) {
        this.stateRepository = stateRepository;
        this.stateMapperInfra = stateMapperInfra;
    }

    @Override
    public Mono<StateRead> getById(Long id) {
        return stateRepository.getById(id)
                .map(stateMapperInfra::toStateRead);
    }

    @Override
    public Flux<StateRead> getAll() {
        return stateRepository.getAll()
                .map(stateMapperInfra::toStateRead);
    }

    @Override
    @Transactional
    public Mono<StateRead> insert(StateEntity stateEntity) {
        return stateRepository.insert(stateEntity)
                .map(stateMapperInfra::toStateRead);
    }

    @Override
    @Transactional
    public Mono<StateRead> update(StateEntity stateEntity) {
        return stateRepository.update(stateEntity)
                .map(stateMapperInfra::toStateRead);
    }

    @Override
    @Transactional
    public Mono<StateRead> upsert(StateEntity stateEntity) {
        if (Objects.isNull(stateEntity.id())) {
            return insert(stateEntity);
        }
        return update(stateEntity);
    }
}
