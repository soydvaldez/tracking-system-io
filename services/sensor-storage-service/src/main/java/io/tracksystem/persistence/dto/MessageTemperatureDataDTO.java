package io.tracksystem.persistence.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Esta clase representa un mensaje de RabbitMQ
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class MessageTemperatureDataDTO implements Serializable {
    public String sensorId;
    public Date timestamp;
    public float temperature;
    public String degree;
}
