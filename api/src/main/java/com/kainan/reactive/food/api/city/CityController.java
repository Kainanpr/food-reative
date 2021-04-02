package com.kainan.reactive.food.api.city;

import com.kainan.reactive.food.business.domain.model.City;
import com.kainan.reactive.food.business.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("v1/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<City> getAll() {
        return cityService.getAll();
    }
}
