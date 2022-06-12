package com.moveo.epicure.exception;

public class AccountBlockedException extends RuntimeException{

    public AccountBlockedException() {
        super("10 failed attempts to login were made. Account will be blocked for 30 minutes");
    }
}
