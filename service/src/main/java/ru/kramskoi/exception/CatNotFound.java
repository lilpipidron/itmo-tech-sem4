package ru.kramskoi.exception;


public class CatNotFound extends CatException {
    public CatNotFound() {
        super("Cat not found");
    }
}
