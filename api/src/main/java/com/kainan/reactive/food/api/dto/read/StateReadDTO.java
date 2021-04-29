package com.kainan.reactive.food.api.dto.read;

import javax.validation.constraints.NotNull;

public record StateReadDTO(
        Long id,
        @NotNull String name
) {
}
