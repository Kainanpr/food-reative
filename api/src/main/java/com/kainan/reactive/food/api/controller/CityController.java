package com.kainan.reactive.food.api.controller;

import com.kainan.reactive.food.api.dto.read.CityReadDTO;
import com.kainan.reactive.food.api.dto.write.CityWriteDTO;
import com.kainan.reactive.food.api.mapper.CityMapperApi;
import com.kainan.reactive.food.business.domain.service.CityService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/cities")
public class CityController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;
    private final CityMapperApi cityMapperApi;

    public CityController(CityService cityService, CityMapperApi cityMapperApi) {
        this.cityService = cityService;
        this.cityMapperApi = cityMapperApi;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CityReadDTO> getById(@PathVariable("id") Long id) {
        return cityService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found")))
                .map(cityMapperApi::toCityReadDTO)
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
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
                    final var cityEntity = cityMapperApi.toCityEntity(null, cityWriteDTO);
                    return cityService.insert(cityEntity);
                })
                .map(cityMapperApi::toCityReadDTO)
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CityReadDTO> update(@PathVariable("id") Long id, @Valid @RequestBody Mono<CityWriteDTO> cityWriteDTOMono) {
        return cityWriteDTOMono
                .flatMap(cityWriteDTO -> {
                    final var cityEntity = cityMapperApi.toCityEntity(id, cityWriteDTO);
                    return cityService.update(cityEntity);
                })
                .map(cityMapperApi::toCityReadDTO)
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
    }
}
