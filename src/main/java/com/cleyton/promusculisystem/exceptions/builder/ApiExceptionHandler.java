package com.cleyton.promusculisystem.exceptions.builder;

import com.cleyton.promusculisystem.exceptions.EntityAlreadyExistsException;
import com.cleyton.promusculisystem.exceptions.InvalidJWTToken;
import com.cleyton.promusculisystem.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiException> notFoundExceptionMethod(NotFoundException e) {
        HttpStatus conflict = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                conflict,
                e.getMessage(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ApiException> entityAlreadyExistsExceptionMethod(EntityAlreadyExistsException e) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                conflict,
                e.getMessage(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler({InvalidJWTToken.class})
    public ResponseEntity<ApiException> invalidJWTToken(InvalidJWTToken e) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                unauthorized,
                e.getMessage(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, unauthorized);
    }
}
