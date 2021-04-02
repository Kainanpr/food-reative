package com.kainan.reactive.food.api.city;

import com.kainan.reactive.food.business.domain.model.City;
import com.kainan.reactive.food.business.domain.model.State;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("v1/cities")
public class CityController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<City> getAll() {
        final State state1 = State.builder()
                .id(1L)
                .name("SP")
                .build();

        final City city1 = City.builder()
                .id(1L)
                .name("São Carlos")
                .state(state1)
                .build();

        final City city2 = City.builder()
                .id(1L)
                .name("Araraquara")
                .state(state1)
                .build();

        return Flux.just(
                city1, city2
        );
    }
}
