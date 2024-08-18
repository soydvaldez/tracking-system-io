package io.tracksystem.persistence.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// @Table(name = "sensor_data_temperature")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "sensor_data")
public class TemperatureDataEntity {
    @Id
    public String id;
    public String sensorId;
    public Date timestamp;
    public double temperature;
    public String degree;

    public TemperatureDataEntity(String sensorId, double temperature, String degree) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.degree = degree;
    }

    public TemperatureDataEntity(String sensorId, Date timestamp, double temperature, String degree) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.degree = degree;
    }
}
