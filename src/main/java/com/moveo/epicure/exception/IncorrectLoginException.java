package com.moveo.epicure.exception;

public class IncorrectLoginException extends RuntimeException{

    public IncorrectLoginException() {
        super("Incorrect email or password.");
    }
}
