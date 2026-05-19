package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {

    @Query("SELECT AVG(r.value) FROM SensorReading r " +
            "WHERE r.sensor.type = :type " +
            "AND r.timestamp BETWEEN :from AND :to")
    Optional<Double> getAverageBySensorTypeAndInterval(
            @Param("type") SensorType type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT r.value FROM SensorReading r " +
            "WHERE r.sensor.type = :type " +
            "AND r.timestamp BETWEEN :from AND :to " +
            "ORDER BY r.timestamp")
    List<Double> findAllBySensorTypeAndInterval(
            @Param("type") SensorType type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

}
