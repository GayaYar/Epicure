package com.moveo.epicure.controller;

import com.moveo.epicure.exception.IncorrectLoginException;
import com.moveo.epicure.exception.LocationNotFoundException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.exception.NullException;
import com.moveo.epicure.exception.model.ErrorCode;
import com.moveo.epicure.exception.model.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class EpicureExceptionHandler {
    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetail handleIncorrectLogin(IncorrectLoginException e) {
        return new ErrorDetail(ErrorCode.INCORRECT_LOGIN.getCode(), e.getMessage());
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetail handleLocationNotFound(LocationNotFoundException e) {
        return new ErrorDetail(ErrorCode.LOCATION_NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetail handleNotFound(NotFoundException e) {
        return new ErrorDetail(ErrorCode.NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(NullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleNull(NullException e) {
        return new ErrorDetail(ErrorCode.NULL_EXCEPTION.getCode(), e.getMessage());
    }
}