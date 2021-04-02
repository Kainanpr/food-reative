package com.kainan.reactive.food.business.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class State {
    private Long id;
    private String name;
}
