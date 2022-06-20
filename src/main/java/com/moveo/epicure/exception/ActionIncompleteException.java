package com.moveo.epicure.exception;

public class ActionIncompleteException extends RuntimeException{

    public ActionIncompleteException() {
        super("Could not complete the action you intended");
    }

    public ActionIncompleteException(String action) {
        super("Could not complete ");
    }
}
