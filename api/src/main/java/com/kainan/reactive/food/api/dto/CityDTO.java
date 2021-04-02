package com.kainan.reactive.food.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDTO {
    private Long id;
    private String name;
}
