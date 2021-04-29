package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.model.entity.StateEntity;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
public class StateRepositoryImpl implements StateRepository {
    private final R2dbcEntityTemplate template;

    public StateRepositoryImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<StateEntity> getById(long id) {
        return template.selectOne(query(where("id").is(id)), StateEntity.class);
    }
}
