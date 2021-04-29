package com.kainan.reactive.food.business.domain.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("state")
public record StateEntity(
        @Id
        Long id,
        String name
) {
}
