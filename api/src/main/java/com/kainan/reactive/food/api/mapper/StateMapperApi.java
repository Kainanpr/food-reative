package com.kainan.reactive.food.api.mapper;

import com.kainan.reactive.food.api.dto.read.StateReadDTO;
import com.kainan.reactive.food.api.dto.write.StateWriteDTO;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;

public interface StateMapperApi {
    StateCommand toStateCommand(Long id, StateWriteDTO stateWriteDTO);

    StateReadDTO toStateReadDTO(StateRead stateRead);
}
