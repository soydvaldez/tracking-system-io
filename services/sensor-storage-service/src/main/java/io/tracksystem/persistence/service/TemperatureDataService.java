package io.tracksystem.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.tracksystem.persistence.model.TemperatureDataEntity;
import io.tracksystem.persistence.repository.TemperatureSensorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TemperatureDataService {
    @Autowired
    TemperatureSensorRepository temperatureSensorRepository;

    public void save(TemperatureDataEntity temperature) {
        TemperatureDataEntity isDataSaved = temperatureSensorRepository.save(temperature);
        log.info("Saved Data {}", isDataSaved.toString());
    }
}
