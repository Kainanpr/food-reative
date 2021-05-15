package com.kainan.reactive.food.infrastructure.kafka.command;

public record StateCommand(
        Long id,
        String name
) {
}
