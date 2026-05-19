package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.constants.Topic;
import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.error.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                        KafkaTemplate<String, AlertDto> kafkaTemplate) {
        this.alertRepository = alertRepository;
        this.sensorRepository = sensorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Alert saveAlert(AlertDto alertDto) {
        Sensor sensor = sensorRepository.findById(alertDto.sensorId())
                .orElseThrow(SensorNotFoundException::new);
        Alert alert = new Alert(alertDto.message(), alertDto.timestamp(), sensor);
        alert = alertRepository.save(alert);

        kafkaTemplate.send(Topic.ALERTS, alertDto);

        return alert;
    }

    public AlertDto findLastAlertBySensorId(UUID sensorId) {
        Alert latestAlert = alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId);
        if (Objects.isNull(latestAlert)) {
            throw new SensorNotFoundException();
        }

        return new AlertDto(latestAlert.getSensor().getId(), latestAlert.getMessage(), latestAlert.getTimestamp());
    }
}
