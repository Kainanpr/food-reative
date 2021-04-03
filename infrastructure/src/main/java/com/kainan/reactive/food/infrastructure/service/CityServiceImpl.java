package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import com.kainan.reactive.food.business.domain.repository.StateRepository;
import com.kainan.reactive.food.business.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Override
    public Flux<CityRead> getAll() {
        return cityRepository.getAll()
                .flatMap((entity) -> Mono.zip(
                        Mono.just(entity),
                        stateRepository.getById(entity.getStateId()),
                        (cityEntity, stateEntity) -> CityRead.builder()
                                .id(cityEntity.getId())
                                .name(cityEntity.getName())
                                .state(StateRead.builder()
                                        .id(stateEntity.getId())
                                        .name(stateEntity.getName())
                                        .build())
                                .build()));
    }

    @Override
    public Mono<CityRead> insert(CityEntity cityEntity) {
        return cityRepository.insert(cityEntity)
                .flatMap(entity -> Mono.zip(Mono.just(entity),
                        stateRepository.getById(entity.getStateId()),
                        (city, state) ->
                                CityRead.builder()
                                        .id(city.getId())
                                        .name(city.getName())
                                        .state(StateRead.builder()
                                                .id(state.getId())
                                                .name(state.getName())
                                                .build())
                                        .build()));
    }
}
