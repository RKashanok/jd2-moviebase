package com.jd2.moviebase.exception;

public class MovieDbValidationOperationException extends RuntimeException {

    private String details;

    public MovieDbValidationOperationException(String message) {
        super(message);
    }

    public MovieDbValidationOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDbValidationOperationException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }
}
