package com.kainan.reactive.food.api.mapper;

import com.kainan.reactive.food.api.dto.read.CityReadDTO;
import com.kainan.reactive.food.api.dto.read.StateReadDTO;
import com.kainan.reactive.food.api.dto.write.CityWriteDTO;
import com.kainan.reactive.food.business.domain.model.entity.CityEntity;
import com.kainan.reactive.food.business.domain.model.read.CityRead;
import org.springframework.stereotype.Service;

@Service
public class CityMapperApiImpl implements CityMapperApi {

    @Override
    public CityEntity toCityEntity(CityWriteDTO cityWriteDTO) {
        return new CityEntity(
                cityWriteDTO.id(),
                cityWriteDTO.name(),
                cityWriteDTO.stateId()
        );
    }

    @Override
    public CityReadDTO toCityReadDTO(CityRead cityRead) {
        final var stateRead = cityRead.state();
        return new CityReadDTO(
                cityRead.id(),
                cityRead.name(),
                new StateReadDTO(
                        stateRead.id(),
                        stateRead.name()
                )
        );
    }
}
