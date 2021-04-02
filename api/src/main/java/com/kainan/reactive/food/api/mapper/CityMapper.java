package com.kainan.reactive.food.api.mapper;

import com.kainan.reactive.food.api.dto.CityDTO;
import com.kainan.reactive.food.business.domain.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toCity(CityDTO cityDTO);
}
