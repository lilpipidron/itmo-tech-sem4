package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kramskoi.exception.OwnerNotFound;

@ControllerAdvice(assignableTypes = OwnerController.class)
public class OwnerExceptionController {
    @ExceptionHandler(OwnerNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOwnerException(OwnerNotFound e) {
        return e.getMessage();
    }
}
