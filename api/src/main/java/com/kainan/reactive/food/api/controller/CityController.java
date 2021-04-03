package com.kainan.reactive.food.api.controller;

import com.kainan.reactive.food.api.dto.CityDTO;
import com.kainan.reactive.food.api.mapper.CityMapper;
import com.kainan.reactive.food.business.domain.model.City;
import com.kainan.reactive.food.business.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<City> getAll() {
        return cityService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<City> insert(@Valid @RequestBody CityDTO cityDTO) {
        final City city = cityMapper.toCity(cityDTO);
        return cityService.insert(city);
    }
}
