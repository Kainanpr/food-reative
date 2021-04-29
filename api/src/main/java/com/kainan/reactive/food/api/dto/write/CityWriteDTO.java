package com.kainan.reactive.food.api.dto.write;

import javax.validation.constraints.NotNull;

public record CityWriteDTO(
        Long id,
        @NotNull String name,
        @NotNull Long stateId
) {
}
