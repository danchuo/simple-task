package com.danchuo.stringunique.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class ConstraintStringExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ConstraintStringExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage;
        if (violations.isEmpty()) {
            errorMessage = "ConstraintViolationException occured.";
        } else {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("\n").append(violation.getMessage()));
            errorMessage = builder.toString();
        }

        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
