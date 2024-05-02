package ru.kramskoi.exception;

public class CatException extends RuntimeException {
    public CatException(String errorMessage) {
        super(errorMessage);
    }
}
