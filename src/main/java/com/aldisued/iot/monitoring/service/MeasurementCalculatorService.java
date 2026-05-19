package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MeasurementCalculatorService {

    public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
        if (deviation <= 0 || deviation > 1) {
            throw new IllegalArgumentException("Deviation must be between 0 and 1");
        }

        double average = values.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        double rangeMin = average - deviation * average;
        double rangeMax = average + deviation * average;

        return values.stream()
                .filter(v -> v >= rangeMin && v <= rangeMax)
                .toList();
    }

    public List<Double> getMovingAverage(List<Double> data, int windowSize) {
        // TODO: Task 10
        return List.of();
    }

}
