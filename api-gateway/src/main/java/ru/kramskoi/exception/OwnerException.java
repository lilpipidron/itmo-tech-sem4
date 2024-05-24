package ru.kramskoi.exception;

public class OwnerException extends RuntimeException {
    public OwnerException(String errorMessage) {
        super(errorMessage);
    }
}
