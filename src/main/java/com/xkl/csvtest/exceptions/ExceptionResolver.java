package com.xkl.csvtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionResolver {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DefaultException.class)
    public Map<String, Object> handleDefaultException(DefaultException ex) {
        return Map.of(
                "error", Map.of(
                        "message", ex.getMessage(),
                        "status", ex.getStatus()
                )
        );
    }
}
