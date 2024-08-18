package io.tracksystem.sensor.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SensorRequestDTO implements Serializable {
    private String sensorId;
    private String sensorName;
    private String location;
    private LocalDateTime installationDate;
    private String status;

    public SensorRequestDTO() {
    }

    public SensorRequestDTO(String sensorName, String location, LocalDateTime installationDate, String status) {
        this.sensorName = sensorName;
        this.location = location;
        this.installationDate = installationDate;
        this.status = status;
    }

    public SensorRequestDTO(String sensorId, String sensorName, String location, LocalDateTime installationDate, String status) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.location = location;
        this.installationDate = installationDate;
        this.status = status;
    }

}
