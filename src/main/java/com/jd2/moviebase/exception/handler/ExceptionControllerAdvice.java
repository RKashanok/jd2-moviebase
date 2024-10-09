package com.jd2.moviebase.exception.handler;

import com.jd2.moviebase.dto.ErrorMessage;
import com.jd2.moviebase.exception.DataCreationException;
import com.jd2.moviebase.exception.DataDeletionException;
import com.jd2.moviebase.exception.DataNotFoundException;
import com.jd2.moviebase.exception.DataUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(DataNotFoundException ex) {
        return new ErrorMessage(ex.getMessage(), LocalDateTime.now(ZoneId.of("UTC")));
    }

    @ExceptionHandler(DataCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleCreationException(DataCreationException ex) {
        return new ErrorMessage(ex.getMessage(), LocalDateTime.now(ZoneId.of("UTC")));
    }

    @ExceptionHandler(DataUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleUpdateException(DataUpdateException ex) {
        return new ErrorMessage(ex.getMessage(), LocalDateTime.now(ZoneId.of("UTC")));
    }

    @ExceptionHandler(DataDeletionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDeletionException(DataDeletionException ex) {
        return new ErrorMessage(ex.getMessage(), LocalDateTime.now(ZoneId.of("UTC")));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleRuntimeException(RuntimeException ex) {
        return new ErrorMessage(ex.getMessage(), LocalDateTime.now(ZoneId.of("UTC")));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format(
                "Invalid type for parameter '%s'. Expected type: %s, but received: %s",
                ex.getName(),
                ex.getRequiredType().getSimpleName(),
                ex.getValue()
        );
        return new ErrorMessage(errorMessage, LocalDateTime.now(ZoneId.of("UTC")));
    }
}
