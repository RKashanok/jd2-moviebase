package com.jd2.moviebase.exception;

import lombok.Getter;

@Getter
public class MovieDbRepositoryOperationException extends RuntimeException {

    private String details;

    public MovieDbRepositoryOperationException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }

    public MovieDbRepositoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
