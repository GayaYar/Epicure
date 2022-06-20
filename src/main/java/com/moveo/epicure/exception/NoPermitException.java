package com.moveo.epicure.exception;

public class NoPermitException extends RuntimeException {

    public NoPermitException() {
        super("You do not have the required authorization for this action");
    }
}
