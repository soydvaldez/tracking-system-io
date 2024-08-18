package io.tracksystem.sensor.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorEntity {

    @Id
    @Column(name = "sensor_id")
    private String sensorId;
    // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "location")
    private String location;

    @Column(name = "installation_date")
    private LocalDateTime installationDate;

    // Usa un ENUM para mapear los estados
    @Column(name = "status")
    private String status;

    public SensorEntity(String sensorName, String location, LocalDateTime installationDate, String status) {
        this.sensorName = sensorName;
        this.location = location;
        this.installationDate = installationDate;
        this.status = status;
    }
}
