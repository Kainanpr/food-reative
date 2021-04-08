package com.kainan.reactive.food.infrastructure.kafka.publisher;

import com.kainan.reactive.food.infrastructure.kafka.event.CityEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;

@Component
@Slf4j
public class CityEventDltProducer extends CityEventCommonProducer {
    public CityEventDltProducer(
            @Value("${kafka.topics.city-event-dlt.name}") String topic,
            KafkaSender<String, CityEvent> kafkaSender
    ) {
        super(topic, kafkaSender);
    }
}
