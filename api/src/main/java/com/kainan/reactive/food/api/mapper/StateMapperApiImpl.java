package com.kainan.reactive.food.api.mapper;

import com.kainan.reactive.food.api.dto.read.StateReadDTO;
import com.kainan.reactive.food.api.dto.write.StateWriteDTO;
import com.kainan.reactive.food.business.domain.model.read.StateRead;
import com.kainan.reactive.food.infrastructure.kafka.command.StateCommand;
import org.springframework.stereotype.Component;

@Component
public class StateMapperApiImpl implements StateMapperApi {

    @Override
    public StateCommand toStateCommand(Long id, StateWriteDTO stateWriteDTO) {
        return new StateCommand(
                id,
                stateWriteDTO.name()
        );
    }

    @Override
    public StateReadDTO toStateReadDTO(StateRead stateRead) {
        return new StateReadDTO(
                stateRead.id(),
                stateRead.name()
        );
    }
}
