package com.moveo.epicure.exception;

public class AccountBlockedException extends RuntimeException{

    public AccountBlockedException() {
        super("10 failed attempts to login were made in the last 30 minutes. Account is blocked.");
    }
}
