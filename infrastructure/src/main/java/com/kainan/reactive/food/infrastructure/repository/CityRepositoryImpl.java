package com.kainan.reactive.food.infrastructure.repository;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl extends GenericCrudRepositoryBase<CityEntity, Long> implements CityRepository {
    public CityRepositoryImpl(R2dbcEntityTemplate template) {
        super(CityEntity.class, template);
    }
}
