package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.repository.GenericCrudRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

public abstract class GenericCrudRepositoryBase<T, ID> implements GenericCrudRepository<T, ID> {
    private final Class<T> typeParameterClass;
    protected final R2dbcEntityTemplate template;

    protected GenericCrudRepositoryBase(Class<T> typeParameterClass, R2dbcEntityTemplate template) {
        this.typeParameterClass = typeParameterClass;
        this.template = template;
    }

    @Override
    public Mono<T> getById(ID id) {
        return template.selectOne(query(where("id").is(id)), typeParameterClass);
    }

    @Override
    public Flux<T> getAll() {
        return template.select(typeParameterClass).all();
    }

    @Override
    public Mono<T> insert(T entity) {
        return template.insert(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        return template.update(entity);
    }
}
