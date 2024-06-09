package com.example.FXDealsTask.exceptions;

public class DealNotFoundException extends RuntimeException {
    public DealNotFoundException(String message) {
        super(message);
    }
}
