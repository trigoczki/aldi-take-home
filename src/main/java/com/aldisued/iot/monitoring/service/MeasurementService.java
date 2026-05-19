package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;

    public MeasurementService(SensorReadingRepository sensorReadingRepository, SensorRepository sensorRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from,
                                                         LocalDateTime to) {
        return sensorReadingRepository.findAllBySensorTypeAndInterval(sensorType, from, to);
    }

    public Optional<Double> getAverageTemperature(LocalDateTime from, LocalDateTime to) {
        return sensorReadingRepository.getAverageBySensorTypeAndInterval(SensorType.TEMPERATURE, from, to);
    }

}
