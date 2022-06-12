package com.moveo.epicure.exception;

public class AccountStillBlockedException extends RuntimeException {

    public AccountStillBlockedException() {
        super("Account is still blocked after 10 failed attempts to login were made in under 30 minutes. "
                + "Block will be removed once 30 minutes have passed from last failed attempt.");
    }

    public AccountStillBlockedException(long minutesLeft) {
        super("Account is still blocked after 10 failed attempts to login were made in under 30 minutes. "
                + "Block will be removed in "+minutesLeft+" minutes.");
    }
}
