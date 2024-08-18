package io.tracksystem.ingest.payload.response;

import java.io.Serializable;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class TemperatureDataResponse implements Serializable {
    public String message;
    public double temperature;

    public TemperatureDataResponse(String message, double temperature) {
        this.message = message;
        this.temperature = temperature;
    }
}
