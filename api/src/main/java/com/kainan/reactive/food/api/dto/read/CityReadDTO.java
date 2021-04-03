package com.kainan.reactive.food.api.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityReadDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private StateReadDTO state;
}
