package com.jd2.moviebase.exception;

public class MovieDbServiceOperationException extends RuntimeException {

    private String details;

    public MovieDbServiceOperationException(String message) {
        super(message);
    }

    public MovieDbServiceOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDbServiceOperationException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }
}
