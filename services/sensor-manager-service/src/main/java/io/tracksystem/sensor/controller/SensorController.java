package io.tracksystem.sensor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.tracksystem.sensor.dto.request.SensorRequestDTO;
import io.tracksystem.sensor.entity.SensorEntity;
import io.tracksystem.sensor.service.SensorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    @Autowired
    SensorService sensorService;

    @GetMapping
    public ResponseEntity<List<SensorEntity>> getAll() {
        return ResponseEntity.ok(sensorService.getAll());
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<SensorEntity> getSensorById(@PathVariable("sensorId") String sensorId) {
        return sensorService.findBySensorId(
                sensorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SensorEntity> newSensor(@RequestBody SensorRequestDTO sensorRequestDTO,
            UriComponentsBuilder uriBuilder) {

        SensorEntity savedSensor = sensorService.save(sensorRequestDTO);
        String locationUri = uriBuilder.path("/sensors/{id}")
                .buildAndExpand(savedSensor.getSensorId()).toUriString();

        return ResponseEntity.created(URI.create(locationUri)).body(savedSensor);
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<SensorEntity> updateSensor(@PathVariable("sensorId") String sensorId,
            @RequestBody SensorEntity sensorInput) {
        Optional<SensorEntity> sensor = sensorService.findBySensorId(sensorId);

        if (sensor.isPresent()) {
            SensorEntity sensorToUpdate = sensor.get();

            sensorToUpdate.setSensorName(sensorInput.getSensorName());
            sensorToUpdate.setLocation(sensorInput.getLocation());
            sensorToUpdate.setStatus(sensorInput.getStatus());

            SensorEntity updatedsensor = sensorService.save(sensorToUpdate);

            return ResponseEntity.ok(updatedsensor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{sensorId}")
    public ResponseEntity<Void> deletesensor(@PathVariable("sensorId") String sensorId) {
        Optional<SensorEntity> sensor = sensorService.findBySensorId(sensorId);

        if (sensor.isPresent()) {
            sensorService.delete(sensor.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
