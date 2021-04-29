package com.kainan.reactive.food.infrastructure.kafka.event;

public record CityEvent(
        Long id,
        String name,
        StateEvent state
) {
}
