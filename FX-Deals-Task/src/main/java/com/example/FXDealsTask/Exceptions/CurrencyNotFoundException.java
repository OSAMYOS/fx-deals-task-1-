package com.example.FXDealsTask.Exceptions;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
    public CurrencyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
