package io.tracksystem.ingest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.tracksystem.ingest.payload.request.TemperatureDataRequest;
import io.tracksystem.ingest.payload.response.TemperatureDataResponse;
import io.tracksystem.ingest.service.TemperatureDataService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/v1/ingestdata")
public class SensorIngestController {

    private final TemperatureDataService temperatureDataService;

    public SensorIngestController(TemperatureDataService temperatureDataService) {
        this.temperatureDataService = temperatureDataService;
    }

    @PostMapping
    public ResponseEntity<TemperatureDataResponse> ingestData(
            @RequestBody TemperatureDataRequest temperatureDataRequest) {
        log.info("Recieved Data: {}", temperatureDataRequest);
        temperatureDataService.save(temperatureDataRequest);
        return ResponseEntity
                .ok(new TemperatureDataResponse("Data Recieved! ", temperatureDataRequest.getTemperature()));
    }
}
