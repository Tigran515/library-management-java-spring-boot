package com.library.libraryspringboot.controller.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage noSuchElementException(NoSuchElementException ex) {
        //add logger here
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Resource Not Found");
    }

    @ExceptionHandler(value = IllegalArgumentException .class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage methodInvalidArgumentException(IllegalArgumentException  ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "User input arguments are not valid");
    }

    @ExceptionHandler(value = DuplicateKeyException.class) //DuplicateElementException is a custom exception, DuplicateKeyException is passed instead
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage duplicateKeyException(DuplicateKeyException ex) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                "Data already exists"
        );
    }
}
