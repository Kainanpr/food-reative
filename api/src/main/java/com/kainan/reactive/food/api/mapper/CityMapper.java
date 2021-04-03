package com.kainan.reactive.food.api.mapper;

import com.kainan.reactive.food.api.dto.read.CityReadDTO;
import com.kainan.reactive.food.api.dto.write.CityWriteDTO;
import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityEntity toCityEntity(CityWriteDTO cityWriteDTO);

    CityReadDTO toCityReadDTO(CityRead cityRead);
}
