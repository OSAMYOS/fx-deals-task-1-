package com.example.FXDealsTask.Exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicateDealException.class, CurrencyNotFoundException.class, SameCurrencyException.class, DealNotFoundException.class})
    public ResponseEntity<Object> handleExceptions(Exception ex) {
        log.error("An exception occurred: ", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        HttpStatus status;
        if (ex instanceof DuplicateDealException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof CurrencyNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof SameCurrencyException) {
            status = HttpStatus.PAYMENT_REQUIRED;
        } else if (ex instanceof DealNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(body, status);
    }

}