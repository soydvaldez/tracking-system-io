package io.tracksystem.persistence.repository;

import io.tracksystem.persistence.model.TemperatureDataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemperatureSensorRepository extends MongoRepository<TemperatureDataEntity, String> {
}
 