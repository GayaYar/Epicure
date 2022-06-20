package com.moveo.epicure.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String existingType) {
        super("This "+existingType+" already exists in the system");
    }
}
