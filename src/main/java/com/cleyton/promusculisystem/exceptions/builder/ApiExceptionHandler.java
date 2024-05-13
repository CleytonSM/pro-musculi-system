package com.cleyton.promusculisystem.exceptions.builder;

import com.cleyton.promusculisystem.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiException> danceClassAlreadyUsingThisDate(CustomException e) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                conflict,
                e.getMessage(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, conflict);
    }
}
