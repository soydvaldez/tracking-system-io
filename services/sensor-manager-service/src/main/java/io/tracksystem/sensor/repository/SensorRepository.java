package io.tracksystem.sensor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.tracksystem.sensor.entity.SensorEntity;

public interface SensorRepository extends JpaRepository<SensorEntity, String> {
     SensorEntity findSensorNameBySensorId(String sensorId);

     Optional<SensorEntity> findBySensorId(String sensorId);
}
