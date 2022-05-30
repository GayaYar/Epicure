package com.moveo.epicure.exception.model;

public enum ErrorCode {
    INCORRECT_LOGIN(1), LOCATION_NOT_FOUND(2), NOT_FOUND(3), NULL_EXCEPTION(4);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
