package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class StateRepositoryImpl extends GenericCrudRepositoryBase<StateEntity, Long> implements StateRepository {
    public StateRepositoryImpl(R2dbcEntityTemplate template) {
        super(StateEntity.class, template);
    }

    @Override
    public Mono<StateEntity> upsert(StateEntity stateEntity) {
        return Mono.just(stateEntity)
                .filter(entity -> !Objects.isNull(entity.id()))
                .switchIfEmpty(insert(stateEntity))
                .flatMap(this::update);
    }
}
