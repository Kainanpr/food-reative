package com.kainan.reactive.food.infrastructure.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityEvent {
    private Long id;
    private String name;
    private StateEvent state;
}
