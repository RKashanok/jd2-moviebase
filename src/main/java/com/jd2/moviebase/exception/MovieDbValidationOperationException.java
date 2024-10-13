package com.jd2.moviebase.exception;

public class MovieDbValidationOperationException extends RuntimeException {

    public MovieDbValidationOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDbValidationOperationException(String message) {
        super(message);
    }
}
