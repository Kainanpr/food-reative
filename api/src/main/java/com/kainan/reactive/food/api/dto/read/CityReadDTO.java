package com.kainan.reactive.food.api.dto.read;

import javax.validation.constraints.NotNull;

public record CityReadDTO(
        Long id,
        @NotNull String name,
        @NotNull StateReadDTO state
) {
}
