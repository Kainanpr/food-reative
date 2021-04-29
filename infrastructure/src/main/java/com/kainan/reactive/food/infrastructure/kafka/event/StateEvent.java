package com.kainan.reactive.food.infrastructure.kafka.event;

public record StateEvent(
        Long id,
        String name
) {
}
