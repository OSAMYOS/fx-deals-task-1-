package com.example.FXDealsTask.Exceptions;

public class DuplicateDealException extends RuntimeException {

    public DuplicateDealException(String message) {
        super(message);
    }

    public DuplicateDealException(String message, Throwable cause) {
        super(message, cause);
    }
}
