package com.jd2.moviebase.exception;

public class MovieDbServiceOperationException extends RuntimeException {

    public MovieDbServiceOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDbServiceOperationException(String message) {
        super(message);
    }
}
