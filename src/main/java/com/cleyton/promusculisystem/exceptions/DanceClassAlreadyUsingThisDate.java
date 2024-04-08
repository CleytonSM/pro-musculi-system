package com.cleyton.promusculisystem.exceptions;

public class DanceClassAlreadyUsingThisDate extends RuntimeException{

    public DanceClassAlreadyUsingThisDate(String message) {
        super(message);
    }
}
