package io.tracksystem.sensor.data;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableRabbit
public class ProducerNewSensorAdded {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(String eventType) {
        log.info("Event: \"" + eventType + "\" Has been Produced {}", eventType);
        rabbitTemplate.convertAndSend(queue.getName(), eventType);
    }
}
