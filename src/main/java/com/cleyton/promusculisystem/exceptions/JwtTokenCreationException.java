package com.cleyton.promusculisystem.exceptions;

public class JwtTokenCreationException extends RuntimeException {
    public JwtTokenCreationException(String message) {
        super(message);
    }
}
