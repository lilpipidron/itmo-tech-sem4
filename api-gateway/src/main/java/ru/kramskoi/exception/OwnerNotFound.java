package ru.kramskoi.exception;

public class OwnerNotFound extends OwnerException {
    public OwnerNotFound() {
        super("Owner not found");
    }
}
