package com.cleyton.promusculisystem.exceptions.builder;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {
    private final HttpStatus status;
    private final String message;
    private final ZonedDateTime timeStamp;

    public ApiException(HttpStatus status, String message, ZonedDateTime timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
