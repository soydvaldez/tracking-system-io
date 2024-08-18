package io.tracksystem.ingest.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.tracksystem.ingest.payload.request.TemperatureDataRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@EnableRabbit
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(TemperatureDataRequest temperatureData) {
        log.info("Event Has been Produced {}", temperatureData.toString());
        rabbitTemplate.convertAndSend(queue.getName(), temperatureData);
    }
}
