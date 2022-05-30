package com.moveo.epicure.exception;

public class NullException extends RuntimeException{

    public NullException() {
        super("Some of the required fields were not found, please fill them.");
    }

    public NullException(String missingField) {
        super("You must enter the "+missingField);
    }
}
