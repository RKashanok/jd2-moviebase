package com.jd2.moviebase.controller;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MovieDbRepositoryOperationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(MovieDbRepositoryOperationException ex) {
        return ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .details(ex.getDetails())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .details(ex.fillInStackTrace().getMessage())
                .build();
    }
}
