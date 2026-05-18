package com.aldisued.iot.monitoring.controller.advice;

import com.aldisued.iot.monitoring.controller.AlertController;
import com.aldisued.iot.monitoring.error.SensorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = AlertController.class)
public class AlertControllerAdvice {

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<Void> handleSensorNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
