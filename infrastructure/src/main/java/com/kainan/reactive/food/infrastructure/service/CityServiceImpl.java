package com.kainan.reactive.food.infrastructure.service;

import com.kainan.reactive.food.business.domain.model.City;
import com.kainan.reactive.food.business.domain.repository.CityRepository;
import com.kainan.reactive.food.business.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public Flux<City> getAll() {
        return cityRepository.getAll();
    }
}
