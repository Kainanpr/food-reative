package com.kainan.reactive.food.api.controller;

import com.kainan.reactive.food.api.dto.read.CityReadDTO;
import com.kainan.reactive.food.api.dto.write.CityWriteDTO;
import com.kainan.reactive.food.api.mapper.CityMapperApi;
import com.kainan.reactive.food.business.domain.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/cities")
public class CityController {
    private final CityService cityService;
    private final CityMapperApi cityMapperApi;

    public CityController(CityService cityService, CityMapperApi cityMapperApi) {
        this.cityService = cityService;
        this.cityMapperApi = cityMapperApi;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CityReadDTO> getAll() {
        return cityService.getAll()
                .map(cityMapperApi::toCityReadDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CityReadDTO> insert(@Valid @RequestBody Mono<CityWriteDTO> cityWriteDTOMono) {
        return cityWriteDTOMono
                .flatMap(cityWriteDTO -> {
                    final var cityEntity = cityMapperApi.toCityEntity(cityWriteDTO);
                    return cityService.insert(cityEntity);
                })
                .map(cityMapperApi::toCityReadDTO)
                .doOnError(ex -> {
                    ex.printStackTrace();
                    Mono.error(ex);
                });
    }
}
