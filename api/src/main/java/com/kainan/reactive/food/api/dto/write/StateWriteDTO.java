package com.kainan.reactive.food.api.dto.write;

import javax.validation.constraints.NotNull;

public record StateWriteDTO(
        Long id,
        @NotNull String name
) {
}
