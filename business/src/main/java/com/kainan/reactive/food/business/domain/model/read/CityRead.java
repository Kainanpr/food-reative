package com.kainan.reactive.food.business.domain.model.read;

public record CityRead(
        Long id,
        String name,
        StateRead state
) {
}
