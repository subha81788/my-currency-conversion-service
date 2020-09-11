package org.subhashis.microservices.mycurrencyconversionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subhashis.microservices.mycurrencyconversionservice.model.ApiError;

import java.time.LocalDateTime;


@ControllerAdvice
public class MyCurrencyConversionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> employeeNotFoundExceptionHandler(EmployeeNotFoundException e) {
        var apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
