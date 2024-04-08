package com.cleyton.promusculisystem.exceptions.builder;

import com.cleyton.promusculisystem.exceptions.DanceClassAlreadyUsingThisDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({DanceClassAlreadyUsingThisDate.class})
    public ResponseEntity<ApiException> danceClassAlreadyUsingThisDate(DanceClassAlreadyUsingThisDate e) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                conflict,
                "There is already a dance class scheduled for this time",
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, conflict);
    }
}
