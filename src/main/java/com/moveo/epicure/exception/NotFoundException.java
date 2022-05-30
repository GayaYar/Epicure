package com.moveo.epicure.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String objName) {
        super("Could not find the "+objName+" you were looking for.");

    }
}
