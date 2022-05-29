package com.moveo.epicure.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entityName) {
        super("Could not find the "+entityName+" you were looking for.");
    }

}
