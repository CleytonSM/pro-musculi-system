package com.cleyton.promusculisystem.exceptions;

public class InvalidJWTToken extends RuntimeException{

    public InvalidJWTToken(String message) {
        super(message);
    }
}
