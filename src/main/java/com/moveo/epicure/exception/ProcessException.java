package com.moveo.epicure.exception;

public class ProcessException extends RuntimeException{

    public ProcessException() {
        super("Encountered problems while processing data...");
    }
}
