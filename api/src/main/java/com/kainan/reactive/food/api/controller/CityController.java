package com.kainan.reactive.food.api.controller;

import com.kainan.reactive.food.api.dto.read.CityReadDTO;
import com.kainan.reactive.food.api.dto.write.CityWriteDTO;
import com.kainan.reactive.food.api.mapper.CityMapper;
import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
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
    public Flux<CityRead> getAll() {
        return cityService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CityReadDTO> insert(@Valid @RequestBody Mono<CityWriteDTO> cityWriteDTOMono) {
        return cityWriteDTOMono.flatMap(cityWriteDTO -> {
            final CityEntity cityEntity = cityMapper.toCityEntity(cityWriteDTO);
            return cityService.insert(cityEntity);
        })
                .map(cityMapper::toCityReadDTO)
                .doOnError(ex -> {
                    ex.printStackTrace();
                    Mono.error(ex);
                });
    }
}
