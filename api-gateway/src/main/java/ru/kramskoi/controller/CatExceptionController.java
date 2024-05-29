package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kramskoi.exception.CatNotFound;

@ControllerAdvice(assignableTypes = CatController.class)
public class CatExceptionController {
    @ExceptionHandler(CatNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCatException(CatNotFound e) {
        return e.getMessage();
    }
}
