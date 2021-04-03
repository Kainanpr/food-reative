package com.kainan.reactive.food.api.dto.write;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateWriteDTO {
    private Long id;
    @NotNull
    private String name;
}
