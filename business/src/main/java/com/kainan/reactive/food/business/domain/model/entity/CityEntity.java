package com.kainan.reactive.food.business.domain.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("city")
public record CityEntity(
        @Id
        Long id,
        String name,
        Long stateId
) {
}
