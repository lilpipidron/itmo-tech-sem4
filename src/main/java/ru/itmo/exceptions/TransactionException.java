package ru.itmo.exceptions;

public class TransactionException extends RuntimeException {
    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}
