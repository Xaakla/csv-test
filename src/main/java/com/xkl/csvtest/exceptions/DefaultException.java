package com.xkl.csvtest.exceptions;

import org.springframework.http.HttpStatus;

public class DefaultException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public DefaultException(String message) {
        super();
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
