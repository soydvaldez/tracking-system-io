package io.tracksystem.persistence.integration.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.tracksystem.persistence.dto.MessageTemperatureDataDTO;
import io.tracksystem.persistence.model.TemperatureDataEntity;
import io.tracksystem.persistence.service.TemperatureDataService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("dev")
public class QueueTemperatureDataConsumer {

    @Autowired
    TemperatureDataService temperatureDataService;

    @RabbitListener(queues = { "${tracksystem.queue.name}" })
    public void recievedData(MessageTemperatureDataDTO messageTemperature) {
        log.info("Recieved Message {}", messageTemperature.toString());
        TemperatureDataEntity temperatureDataEntity = mapperDTOToEntity(messageTemperature);
        log.info("Run: {}", TemperatureDataService.class);
        temperatureDataService.save(temperatureDataEntity);
    }

    public TemperatureDataEntity mapperDTOToEntity(MessageTemperatureDataDTO messageTemperature) {
        TemperatureDataEntity mapper = new TemperatureDataEntity();
        mapper.setSensorId(messageTemperature.getSensorId());
        mapper.setTimestamp(messageTemperature.getTimestamp());
        mapper.setTemperature(messageTemperature.getTemperature());
        mapper.setDegree(messageTemperature.getDegree());
        return mapper;
    }
}

// LocalDateTime localDateTime = LocalDateTime.now(); // Obtener la fecha y hora
// actuales
// Date currentDate =
// Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); //
// Convertir LocalDateTime a Date