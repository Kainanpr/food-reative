package com.kainan.reactive.food.api.controller;

import com.kainan.reactive.food.api.dto.read.StateReadDTO;
import com.kainan.reactive.food.api.dto.write.StateWriteDTO;
import com.kainan.reactive.food.api.mapper.StateMapperApi;
import com.kainan.reactive.food.business.domain.service.StateService;
import com.kainan.reactive.food.infrastructure.kafka.publisher.StateEventProducer;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/states")
public class StateController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StateController.class);

    private final StateService stateService;
    private final StateEventProducer stateEventProducer;
    private final StateMapperApi stateMapperApi;

    public StateController(
            StateService stateService,
            StateEventProducer stateEventProducer,
            StateMapperApi stateMapperApi
    ) {
        this.stateService = stateService;
        this.stateEventProducer = stateEventProducer;
        this.stateMapperApi = stateMapperApi;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<StateReadDTO> getById(@PathVariable("id") Long id) {
        return stateService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "State not found")))
                .map(stateMapperApi::toStateReadDTO)
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<StateReadDTO> getAll() {
        return stateService.getAll()
                .map(stateMapperApi::toStateReadDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> insert(@Valid @RequestBody Mono<StateWriteDTO> stateWriteDTOMono) {
        return stateWriteDTOMono
                .flatMap(stateWriteDTO -> {
                    final var stateEvent = stateMapperApi.toStateEvent(null, stateWriteDTO);
                    return stateEventProducer.sendEvent(null, stateEvent).then();
                })
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> update(@PathVariable("id") Long id, @Valid @RequestBody Mono<StateWriteDTO> stateWriteDTOMono) {
        return stateService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "State not found")))
                .then(stateWriteDTOMono)
                .flatMap(stateWriteDTO -> {
                    final var stateEvent = stateMapperApi.toStateEvent(id, stateWriteDTO);
                    return stateEventProducer.sendEvent(stateEvent.id().toString(), stateEvent).then();
                })
                .doOnError(error -> log.error("An error occurred: {}", error.getMessage()));
    }
}
