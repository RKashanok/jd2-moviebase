package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleRuntimeException(RuntimeException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage;
        errorMessage = String.format(
                "Invalid type for parameter '%s'. Expected type: %s, but received: %s",
                ex.getName(),
                ex.getRequiredType().getSimpleName(),
                ex.getValue()
        );
        return new ErrorMessage(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleGeneralException(Exception ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
