package io.tracksystem.sensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.tracksystem.sensor.data.ProducerNewSensorAdded;
import io.tracksystem.sensor.dto.request.SensorRequestDTO;
import io.tracksystem.sensor.entity.SensorEntity;
import io.tracksystem.sensor.repository.SensorRepository;

import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private ProducerNewSensorAdded producerNewSensorAdded;

    @Autowired
    SensorRepository sensorRepository;

    public List<SensorEntity> getAll() {
        return sensorRepository.findAll();
    }

    public SensorEntity save(SensorRequestDTO sensorRequestDTO) {
        SensorEntity sensorAdded = sensorRepository.save(convertRequestDTOtoEntity(sensorRequestDTO));
        producerNewSensorAdded.send("NewSensorAdded");
        return sensorAdded;
    }

    public Optional<SensorEntity> findBySensorId(String sensorId) {
        return sensorRepository.findBySensorId(sensorId);
    }

    public SensorEntity save(SensorEntity sensorToUpdate) {
        return sensorRepository.save(sensorToUpdate);
    }

    public void delete(SensorEntity sensorEntity) {
        sensorRepository.delete(sensorEntity);
    }

    private SensorEntity convertRequestDTOtoEntity(SensorRequestDTO sensorRequestDTO) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setSensorId(sensorRequestDTO.getSensorId());
        sensorEntity.setSensorName(sensorRequestDTO.getSensorName());
        sensorEntity.setLocation(sensorRequestDTO.getLocation());
        sensorEntity.setInstallationDate(sensorRequestDTO.getInstallationDate());
        sensorEntity.setStatus(sensorRequestDTO.getStatus());

        return sensorEntity;
    }

    private SensorRequestDTO convertEntityToRequestDTO(SensorEntity sensorEntity) {
        SensorRequestDTO sensorDTO = new SensorRequestDTO();
        sensorDTO.setSensorId(sensorEntity.getSensorId());
        sensorDTO.setSensorName(sensorEntity.getSensorName());
        sensorDTO.setLocation(sensorEntity.getLocation());
        sensorDTO.setInstallationDate(sensorEntity.getInstallationDate());
        sensorDTO.setStatus(sensorEntity.getStatus());

        return sensorDTO;
    }
}
