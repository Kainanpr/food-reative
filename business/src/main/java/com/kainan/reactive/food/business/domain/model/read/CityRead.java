package com.kainan.reactive.food.business.domain.model.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRead {
    private Long id;
    private String name;
    private StateRead state;
}
