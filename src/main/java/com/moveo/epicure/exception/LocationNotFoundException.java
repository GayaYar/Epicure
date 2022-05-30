package com.moveo.epicure.exception;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException() {
        super("Could not pin your location");
    }
}
